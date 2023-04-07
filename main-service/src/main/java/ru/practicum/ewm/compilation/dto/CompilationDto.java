package ru.practicum.ewm.compilation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto {
    private Long id;
    private String title;
    private Boolean pinned;
    private List<EventShortDto> events;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EventShortDto {
        private Long id;
        private String title;
        private String annotation;
        private String description;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime eventDate;
        private Boolean paid = Boolean.FALSE;

        private CategoryDto category;
        private Integer confirmedRequests = 0;
        private Long views = 0L;
        private UserShortDto initiator;
    }

    public static EventShortDto toEventShortDto(Event event) {
        return new EventShortDto(event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getEventDate(),
                event.getPaid(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getConfirmedRequests(),
                event.getViews(),
                UserMapper.toUserShortDto(event.getInitiator()));
    }
}
