package com.example.userauthservice_june2025.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequestDto {
    private String name;

    private String email;

    private String password;

    private String phoneNumber;
}
