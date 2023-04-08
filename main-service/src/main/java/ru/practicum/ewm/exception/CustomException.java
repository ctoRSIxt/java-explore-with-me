package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomException extends RuntimeException {
    private String reason;
    private String message;

    public CustomException(String reason, String message) {
        super();
        this.reason = reason;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}