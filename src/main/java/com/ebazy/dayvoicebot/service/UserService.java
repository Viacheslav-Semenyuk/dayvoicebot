package com.ebazy.dayvoicebot.service;

import com.ebazy.dayvoicebot.entity.User;

public interface UserService {

    User findByChatId(String chatId);

    User save (User user);

    boolean existsByChatId(String chatId);
}
