package ru.practicum.ewm.event.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class AtLeast2HoursValidator implements ConstraintValidator<AtLeast2Hours, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime eventDate, ConstraintValidatorContext context) {
        return eventDate.isAfter(LocalDateTime.now().plusHours(2));
    }
}