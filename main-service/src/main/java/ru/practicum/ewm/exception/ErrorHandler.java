package ru.practicum.ewm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handle(NotFoundException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND
        );
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiError handle(MethodArgumentNotValidException e) {
        return new ApiError(
                "Incorrectly made request.",
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handle(CustomValidationException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConditionsNotMetException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handle(ConditionsNotMetException e) {
        return new ApiError(
                e.getReason(),
                e.getMessage(),
                LocalDateTime.now(),
                HttpStatus.CONFLICT
        );
    }


}
