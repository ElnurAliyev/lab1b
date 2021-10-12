package com.elnur.lab1b.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class CustomException extends RuntimeException {
    private final String code;
    private final String message;
    
    protected CustomException(String code, String message) {
        super(code);
        this.code = code;
        this.message = message;
    }
}
