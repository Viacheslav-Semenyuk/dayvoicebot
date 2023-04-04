package com.ebazy.dayvoicebot.bot;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ebazy.dayvoicebot.bot.handler.KeyboardHandler;
import com.ebazy.dayvoicebot.entity.User;
import com.ebazy.dayvoicebot.service.UserService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String token_bot;
    @Value("${telegram.bot.username}")
    private String username_bot;

    private static final String TOKEN_BOT = System.getenv("TOKEN");
    private static final String BOT_USERNAME = System.getenv("BOT_USERNAME");

    @Autowired
    private KeyboardHandler keyboardHandler;

    @Autowired
    private UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            System.out.println(chatId);
            User user = userService.findByChatId(chatId);

            if (update.hasMessage() && update.getMessage().hasText()) {
                String text = update.getMessage().getText();

                if (user.getChatId() == null && text.equals("/start")) {
                    userService.save(User.builder()
                            .chatId(chatId)
                            .username("@" + update.getMessage().getFrom().getUserName())
                            .build());

                    executeAnimation(SendAnimation.builder()
                            .chatId(chatId)
                            .animation(new InputFile(new File("src/main/resources/static/settings.mp4")))
                            .caption("""
                                    Привет! Сначала заполни анкету ↓     
                                                                        
                                    Из какой ты страны?
                                               """)
                            .replyMarkup(keyboardHandler.inlineCountry())
                            .build());
                } else if (user.getGender() != null && user.getCountry() != null && user.getAge() != null) {
                    executeAnimation(SendAnimation.builder()
                            .chatId(chatId)
                            .animation(new InputFile(new File("src/main/resources/static/settings.mp4")))
                            .caption("Анкета: " +
                                    "\n  Пол: " + user.getGender() +
                                    "\n  Страна: " + user.getCountry() +
                                    "\n  Возраст: " + user.getAge())
                            .replyMarkup(keyboardHandler.inlineProfile())
                            .build());
                }
            }

            if (update.hasMessage() && update.getMessage().hasVoice()) {
                String fileId = update.getMessage().getVoice().getFileId();
            }

        }

        if (update.hasCallbackQuery()) {
            String chatId = String.valueOf(update.getCallbackQuery().getMessage().getChatId());
            Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
            User user = userService.findByChatId(chatId);

            if (update.getCallbackQuery().getData().equals("Украина")) {
                user.setCountry("Украина");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Твой пол:")
                        .replyMarkup(keyboardHandler.inlineGender())
                        .build());

            }
            if (update.getCallbackQuery().getData().equals("Россия")) {
                user.setCountry("Россия");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Твой пол:")
                        .replyMarkup(keyboardHandler.inlineGender())
                        .build());
            }
            if (update.getCallbackQuery().getData().equals("Беларусь")) {
                user.setCountry("Беларусь");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Твой пол:")
                        .replyMarkup(keyboardHandler.inlineGender())
                        .build());
            }

            if (update.getCallbackQuery().getData().equals("Мужской")) {
                user.setGender("Мужской");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Твой возраст:")
                        .replyMarkup(keyboardHandler.inlineAge())
                        .build());
            }
            if (update.getCallbackQuery().getData().equals("Женский")) {
                user.setGender("Женский");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Твой возраст:")
                        .replyMarkup(keyboardHandler.inlineAge())
                        .build());
            }

            if (update.getCallbackQuery().getData().equals("Меньше 18")) {
                user.setAge("Меньше 18");
                userService.save(user);
                executeAnimation(SendAnimation.builder()
                        .chatId(chatId)
                        .animation(new InputFile(new File("src/main/resources/static/settings.mp4")))
                        .caption("Анкета: " +
                                "\n  Пол: " + user.getGender() +
                                "\n  Страна: " + user.getCountry() +
                                "\n  Возраст: " + user.getAge())
                        .replyMarkup(keyboardHandler.inlineProfile())
                        .replyMarkup(keyboardHandler.replyKeyboardMain())
                        .build());
                deleteMessage(chatId, messageId);
            }
            if (update.getCallbackQuery().getData().equals("Больше 18")) {
                user.setAge("Больше 18");
                userService.save(user);
                executeAnimation(SendAnimation.builder()
                        .chatId(chatId)
                        .animation(new InputFile(new File("src/main/resources/static/settings.mp4")))
                        .caption("Анкета: " +
                                "\n  Пол: " + user.getGender() +
                                "\n  Страна: " + user.getCountry() +
                                "\n  Возраст: " + user.getAge())
                        .replyMarkup(keyboardHandler.inlineProfile())
                        .replyMarkup(keyboardHandler.replyKeyboardMain())
                        .build());
                deleteMessage(chatId, messageId);
            }

            if (update.getCallbackQuery().getData().equals("profile")) {
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Измени анкету ↓")
                        .replyMarkup(keyboardHandler.inlineEditProfile(user))
                        .build());
            }

            if (update.getCallbackQuery().getData().equals("editAge")) {
                if (user.getAge().equals("Меньше 18")) {
                    user.setAge("Больше 18");
                } else {
                    user.setAge("Меньше 18");
                }
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Измени анкету ↓")
                        .replyMarkup(keyboardHandler.inlineEditProfile(user))
                        .build());
                userService.save(user);
            }
            if (update.getCallbackQuery().getData().equals("editGender")) {
                if (user.getGender().equals("Мужской")) {
                    user.setGender("Женский");
                } else {
                    user.setGender("Мужской");
                }
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Измени анкету ↓")
                        .replyMarkup(keyboardHandler.inlineEditProfile(user))
                        .build());
                userService.save(user);
            }
            if (update.getCallbackQuery().getData().equals("editCountry")) {
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Выбор страны помогает находить тебе подходящих собеседников.")
                        .replyMarkup(keyboardHandler.inlineEditCountry())
                        .build());
                userService.save(user);
            }
            if (update.getCallbackQuery().getData().equals("editUkraine")) {
                user.setCountry("Украина");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Измени анкету ↓")
                        .replyMarkup(keyboardHandler.inlineEditProfile(user))
                        .build());

            }
            if (update.getCallbackQuery().getData().equals("editRussia")) {
                user.setCountry("Россия");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Измени анкету ↓")
                        .replyMarkup(keyboardHandler.inlineEditProfile(user))
                        .build());
            }
            if (update.getCallbackQuery().getData().equals("editBelarus")) {
                user.setCountry("Беларусь");
                userService.save(user);
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Измени анкету ↓")
                        .replyMarkup(keyboardHandler.inlineEditProfile(user))
                        .build());
            }
            if (update.getCallbackQuery().getData().equals("back")) {
                executeEditMessageAnimation(EditMessageCaption.builder()
                        .chatId(chatId)
                        .messageId(messageId)
                        .caption("Анкета: " +
                                "\n Пол: " + user.getGender() +
                                "\n Страна: " + user.getCountry() +
                                "\n Возраст: " + user.getAge())
                        .replyMarkup(keyboardHandler.inlineProfile())
                        .build());
            }
            if (update.getCallbackQuery().getData().equals("deleteMessage")) {
                deleteMessage(chatId, messageId);
            }


        }
    }


    public void executeVoice(SendVoice sendVoice) {
        try {
            execute(sendVoice);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executePhoto(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeText(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void deleteMessage(String chatId, Integer messageId) {
        try {
            execute(DeleteMessage.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeAnimation(SendAnimation sendAnimation) {
        try {
            execute(sendAnimation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeEditMessage(EditMessageCaption EditMessageCaption) {
        try {
            execute(EditMessageCaption);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeEditMessageAnimation(EditMessageCaption editMessageCaption) {
        try {
            execute(editMessageCaption);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return username_bot;
    }

    @Override
    public String getBotToken() {
        return token_bot;
    }
}
