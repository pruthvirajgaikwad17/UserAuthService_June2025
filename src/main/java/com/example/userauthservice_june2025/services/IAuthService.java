package com.example.userauthservice_june2025.services;

import com.example.userauthservice_june2025.models.User;

public interface IAuthService {
    User signup(String name,String email, String password, String phoneNumber);

    User login(String email, String password);

    Boolean validateToken(String token, Long userId);
}
