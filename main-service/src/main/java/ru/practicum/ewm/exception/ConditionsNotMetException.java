package ru.practicum.ewm.exception;


public class ConditionsNotMetException extends CustomException {
    public ConditionsNotMetException(String reason, String message) {
        super(reason, message);
    }
}