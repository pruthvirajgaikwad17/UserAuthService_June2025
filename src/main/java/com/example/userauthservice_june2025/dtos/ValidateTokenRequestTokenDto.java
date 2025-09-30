package com.example.userauthservice_june2025.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateTokenRequestTokenDto {

    private String token;
    private long userId;
}
