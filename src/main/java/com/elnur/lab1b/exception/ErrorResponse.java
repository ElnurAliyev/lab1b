package com.elnur.lab1b.exception;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String code;
    private String message;
    
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
