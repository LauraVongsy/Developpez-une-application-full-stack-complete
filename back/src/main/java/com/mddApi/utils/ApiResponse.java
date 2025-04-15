package com.mddApi.utils;


import org.springframework.stereotype.Service;


/**
 * converti la réponse envoyée au frontend en json
 */
public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
