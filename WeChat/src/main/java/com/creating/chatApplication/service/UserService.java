package com.creating.chatApplication.service;

import com.creating.chatApplication.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User getCurrentUser();

    User saveUser(User user);

    String getFCMTokenByEmail(String email);

    void saveFCMToken(String email, String token);

    User findByVerificationToken(String token);

    User findByVerificationTokenAndUserId(int user_id, String token);

    void DeleteUserById(int id);

    List<String> findEmailsByQuery(String query);

    String getUserNameById(int id);
}
