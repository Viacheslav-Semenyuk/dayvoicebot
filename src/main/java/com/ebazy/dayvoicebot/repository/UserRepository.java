package com.ebazy.dayvoicebot.repository;

import com.ebazy.dayvoicebot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByChatId(String chatId);

    boolean existsByChatId(String chatId);
}
