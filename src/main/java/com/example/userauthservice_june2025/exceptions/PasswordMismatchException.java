package com.example.userauthservice_june2025.exceptions;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message){
        super(message);
    }
}
