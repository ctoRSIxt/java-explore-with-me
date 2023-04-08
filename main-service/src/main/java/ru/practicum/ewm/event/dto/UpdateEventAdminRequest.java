package ru.practicum.ewm.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.event.enums.StateAction;
import ru.practicum.ewm.event.location.Location;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {
    @Size(min = 3, max = 120, message = "Title length should be min = 3, max = 120")
    private String title;

    @Size(min = 20, max = 2000, message = "Annotation length should be min = 20, max = 2000")
    private String annotation;

    @Size(min = 20, max = 7000, message = "Description length should be min = 20, max = 7000")
    private String description;

    private Location location;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private Boolean paid;

    private Long category;

    private Boolean requestModeration;

    @Min(0)
    private Integer participantLimit;

    private StateAction stateAction;
}
