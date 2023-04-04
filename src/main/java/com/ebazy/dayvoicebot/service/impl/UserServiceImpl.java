package com.ebazy.dayvoicebot.service.impl;

import com.ebazy.dayvoicebot.entity.User;
import com.ebazy.dayvoicebot.repository.UserRepository;
import com.ebazy.dayvoicebot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByChatId(String chatId) {
        return userRepository.findByChatId(chatId).orElse(new User());
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean existsByChatId(String chatId) {
        return userRepository.existsByChatId(chatId);
    }
}
