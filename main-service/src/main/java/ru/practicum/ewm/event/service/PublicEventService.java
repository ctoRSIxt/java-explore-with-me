package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.List;

public interface PublicEventService {

    List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid,
                                   String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                   String sort, Integer from, Integer size);

    EventFullDto findEventById(Long id);
}
