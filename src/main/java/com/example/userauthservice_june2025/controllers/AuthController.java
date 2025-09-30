package com.example.userauthservice_june2025.controllers;

import com.example.userauthservice_june2025.dtos.LoginRequestDto;
import com.example.userauthservice_june2025.dtos.SignupRequestDto;
import com.example.userauthservice_june2025.dtos.UserDto;
import com.example.userauthservice_june2025.dtos.ValidateTokenRequestTokenDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {

    //signup
    //login
    //validateToken

    // ToDo :
    //logout - homework
    //forgetPassword - homework

    @PostMapping("/signup")
    public UserDto signUp(@RequestBody SignupRequestDto signupRequestDto) {
        return null;
    }

    // we are creating a token while login we should be using post
    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return null;
    }

    @PostMapping("/validateToken")
    public Boolean validateToken(@RequestBody ValidateTokenRequestTokenDto validateTokenRequestTokenDto) {
        return null;
    }
}
