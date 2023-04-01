package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.ewm.event.location.Location;
import ru.practicum.ewm.event.validators.AtLeast2Hours;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class NewEventDto {

    @NotNull(message = "Title cannot be null")
    @Size(min = 3, max = 120, message = "Title length should be min = 3, max = 120")
    private String title;

    @NotNull(message = "Annotation cannot be null")
    @Size(min = 20, max = 2000, message = "Annotation length should be min = 20, max = 2000")
    private String annotation;

    @NotNull(message = "Description cannot be null")
    @Size(min = 3, max = 120, message = "Description length should be min = 20, max = 7000")
    private String description;

    @NotNull(message = "Location cannot be null")
    private Location location;

    @NotNull(message = "EventDate cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @AtLeast2Hours
    private LocalDateTime eventDate;

    private Boolean paid = Boolean.FALSE;

    @NotNull(message = "Category cannot be null")
    private Long category;

    private Boolean requestModeration = Boolean.TRUE;

    @Min(0)
    private Integer participantLimit = 0;
}
