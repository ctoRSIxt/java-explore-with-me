package ru.practicum.ewm.exception;

public class ConflictRequestException extends CustomException {
    public ConflictRequestException(String reason, String message) {
        super(reason, message);
    }
}
