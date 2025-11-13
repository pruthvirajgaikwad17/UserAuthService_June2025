package com.example.userauthservice_june2025.services;

import com.example.userauthservice_june2025.dtos.UserDto;
import com.example.userauthservice_june2025.models.User;
import com.example.userauthservice_june2025.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public User getUserDetailsBasedOnId(Long id) {
        System.out.println("getUserDetailsBasedOnId "+ id);
        User user = userRepo.findById(id).get();
        System.out.println(user.getName());
        return user;
    }
}
