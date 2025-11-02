package com.example.ztp_lab1.exception;

public class BannedPhraseException extends RuntimeException{
    public BannedPhraseException(String message) {
        super(message);
    }
}
