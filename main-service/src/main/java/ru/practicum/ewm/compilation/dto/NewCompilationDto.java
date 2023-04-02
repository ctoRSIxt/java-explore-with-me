package ru.practicum.ewm.compilation.dto;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotNull;
import java.util.List;

public class NewCompilationDto {

    @NotNull(message = "Title of compilation cannot be null")
    private String title;

    @UniqueElements
    private List<Long> events;

    private Boolean pinned = Boolean.FALSE;
}
