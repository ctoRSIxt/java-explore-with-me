package ru.practicum.ewm.event.mapper;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.dto.UserShortDto;
import ru.practicum.ewm.user.mapper.UserMapper;

import java.time.LocalDateTime;

public class EventMapper {
    public static EventFullDto toEventFullDto(Event event) {
        return new EventFullDto(event.getId(),
                event.getTitle(),
                event.getAnnotation(),
                event.getDescription(),
                event.getLocation(),
                event.getEventDate(),
                event.getPaid(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                event.getRequestModeration(),
                event.getParticipantLimit(),
                event.getConfirmedRequests(),
                event.getViews(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getCreateOn(),
                event.getPublishedOn(),
                event.getState());
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