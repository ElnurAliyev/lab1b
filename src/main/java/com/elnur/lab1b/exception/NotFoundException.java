package com.elnur.lab1b.exception;

public class NotFoundException extends CustomException {

    public NotFoundException(String code, String message) {
        super(code, message);
    }
}
