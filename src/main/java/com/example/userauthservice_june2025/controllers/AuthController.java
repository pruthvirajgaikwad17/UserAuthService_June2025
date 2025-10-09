package com.example.userauthservice_june2025.controllers;

import com.example.userauthservice_june2025.dtos.LoginRequestDto;
import com.example.userauthservice_june2025.dtos.SignupRequestDto;
import com.example.userauthservice_june2025.dtos.UserDto;
import com.example.userauthservice_june2025.dtos.ValidateTokenRequestTokenDto;
import com.example.userauthservice_june2025.exceptions.TokenInvalidException;
import com.example.userauthservice_june2025.models.User;
import com.example.userauthservice_june2025.services.IAuthService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class AuthController {

    @Autowired
    private IAuthService authService;
    @Autowired
    private IAuthService iAuthService;
    //signup
    //login
    //validateToken

    // ToDo :
    //logout - homework
    //forgetPassword - homework

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignupRequestDto signupRequestDto) {
        User user = iAuthService.signup(signupRequestDto.getName(), signupRequestDto.getEmail(), signupRequestDto.getPassword(), signupRequestDto.getPhoneNumber());
        UserDto userDto = from(user);
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    // we are creating a token while login we should be using post
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Pair<User, String> response = iAuthService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        User user = response.a;
        String token = response.b;

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>(); // multiple values for the single key
        headers.add(HttpHeaders.SET_COOKIE, token);
        return new ResponseEntity<>(from(user), headers, HttpStatus.OK);
    }

    @PostMapping("/validateToken")
    public ResponseEntity<Void> validateToken(@RequestBody ValidateTokenRequestTokenDto validateTokenRequestTokenDto) {
        Boolean result = authService.validateToken(validateTokenRequestTokenDto.getToken(), validateTokenRequestTokenDto.getUserId());
        if (!result) {
            throw new TokenInvalidException("Please login again");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    UserDto from(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        return  userDto;
    }
}
