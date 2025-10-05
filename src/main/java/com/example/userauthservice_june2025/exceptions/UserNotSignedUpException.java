package com.example.userauthservice_june2025.exceptions;

public class UserNotSignedUpException extends RuntimeException{

    public UserNotSignedUpException(String message){
        super(message);
    }
}
