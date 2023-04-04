package ru.practicum.ewm.event.enums;

import ru.practicum.ewm.exception.CustomValidationException;

import java.util.List;
import java.util.stream.Collectors;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED;

    public static State validateState(String stateString) {
        State state = null;
        try {
            state = State.valueOf(stateString);
        } catch (IllegalArgumentException e) {
            throw new CustomValidationException("Incorrectly made request.",
                    "Unknown state: " + stateString);
        }
        return state;
    }

    public static List<State> validateState(List<String> stateString) {
        return stateString.stream()
                .map(State::validateState)
                .collect(Collectors.toList());
    }
}
