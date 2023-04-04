package ru.practicum.ewm.exception;


public class CustomValidationException extends CustomException {
    public CustomValidationException(String reason, String message) {
        super(reason, message);
    }
}