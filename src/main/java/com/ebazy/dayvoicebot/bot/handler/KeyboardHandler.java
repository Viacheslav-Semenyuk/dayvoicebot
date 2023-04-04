package com.ebazy.dayvoicebot.bot.handler;

import com.ebazy.dayvoicebot.entity.User;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class KeyboardHandler {

    public InlineKeyboardMarkup inlineCountry() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton ukraineButton = new InlineKeyboardButton();
        InlineKeyboardButton russiaButton = new InlineKeyboardButton();
        InlineKeyboardButton belarusButton = new InlineKeyboardButton();
        ukraineButton.setText("Украина");
        ukraineButton.setCallbackData("Украина");
        russiaButton.setText("Россия");
        russiaButton.setCallbackData("Россия");
        belarusButton.setText("Беларусь");
        belarusButton.setCallbackData("Беларусь");
        List<InlineKeyboardButton> listButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> listButtonsRow2 = new ArrayList<>();

        listButtonsRow1.add(russiaButton);
        listButtonsRow1.add(ukraineButton);

        listButtonsRow2.add(belarusButton);

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();

        rowsList.add(listButtonsRow1);
        rowsList.add(listButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineEditCountry() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton ukraineButton = new InlineKeyboardButton();
        InlineKeyboardButton russiaButton = new InlineKeyboardButton();
        InlineKeyboardButton belarusButton = new InlineKeyboardButton();
        ukraineButton.setText("Украина");
        ukraineButton.setCallbackData("editUkraine");
        russiaButton.setText("Россия");
        russiaButton.setCallbackData("editRussia");
        belarusButton.setText("Беларусь");
        belarusButton.setCallbackData("editBelarus");
        List<InlineKeyboardButton> listButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> listButtonsRow2 = new ArrayList<>();

        listButtonsRow1.add(russiaButton);
        listButtonsRow1.add(ukraineButton);

        listButtonsRow2.add(belarusButton);

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();

        rowsList.add(listButtonsRow1);
        rowsList.add(listButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineGender() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton man = new InlineKeyboardButton();
        InlineKeyboardButton woman = new InlineKeyboardButton();
        man.setText("Мужской");
        man.setCallbackData("Мужской");
        woman.setText("Женский");
        woman.setCallbackData("Женский");

        List<InlineKeyboardButton> listButtonsRow1 = new ArrayList<>();
        listButtonsRow1.add(man);
        listButtonsRow1.add(woman);

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        rowsList.add(listButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineAge() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton less = new InlineKeyboardButton();
        InlineKeyboardButton more = new InlineKeyboardButton();
        less.setText("Меньше 18");
        less.setCallbackData("Меньше 18");
        more.setText("Больше 18");
        more.setCallbackData("Больше 18");

        List<InlineKeyboardButton> listButtonsRow1 = new ArrayList<>();
        listButtonsRow1.add(less);
        listButtonsRow1.add(more);

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        rowsList.add(listButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineProfile() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton profile = new InlineKeyboardButton();
        InlineKeyboardButton deleteMessage = new InlineKeyboardButton();
        profile.setText("Анкета");
        profile.setCallbackData("profile");
        deleteMessage.setText("x");
        deleteMessage.setCallbackData("deleteMessage");

        List<InlineKeyboardButton> listButtonsRow1 = new ArrayList<>();
        listButtonsRow1.add(profile);
        listButtonsRow1.add(deleteMessage);

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        rowsList.add(listButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup inlineEditProfile(User user) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton editAge = new InlineKeyboardButton();
        InlineKeyboardButton editGender = new InlineKeyboardButton();
        InlineKeyboardButton editCountry = new InlineKeyboardButton();
        InlineKeyboardButton back = new InlineKeyboardButton();
        editAge.setText("Возраст: " + user.getAge());
        editAge.setCallbackData("editAge");
        editGender.setText("Пол: " + user.getGender());
        editGender.setCallbackData("editGender");
        editCountry.setText("Страна: " + user.getCountry());
        editCountry.setCallbackData("editCountry");
        back.setText("← Назад ");
        back.setCallbackData("back");

        List<InlineKeyboardButton> listButtonsRow1 = new ArrayList<>();
        List<InlineKeyboardButton> listButtonsRow2 = new ArrayList<>();
        List<InlineKeyboardButton> listButtonsRow3 = new ArrayList<>();
        List<InlineKeyboardButton> listButtonsRow4 = new ArrayList<>();
        listButtonsRow1.add(editAge);
        listButtonsRow2.add(editGender);
        listButtonsRow3.add(editCountry);
        listButtonsRow4.add(back);

        List<List<InlineKeyboardButton>> rowsList = new ArrayList<>();
        rowsList.add(listButtonsRow1);
        rowsList.add(listButtonsRow2);
        rowsList.add(listButtonsRow3);
        rowsList.add(listButtonsRow4);

        inlineKeyboardMarkup.setKeyboard(rowsList);

        return inlineKeyboardMarkup;
    }

    public ReplyKeyboardMarkup replyKeyboardMain() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardFirstRow.add("Начать диалог");
        keyboardSecondRow.add("Мой профиль");

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboard);

        return replyKeyboardMarkup;
    }

}
