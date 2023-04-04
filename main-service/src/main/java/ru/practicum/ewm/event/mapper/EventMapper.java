package ru.practicum.ewm.event.mapper;

import ru.practicum.ewm.category.mapper.CategoryMapper;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.mapper.UserMapper;

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
}