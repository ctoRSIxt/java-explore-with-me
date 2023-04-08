package ru.practicum.ewm.event.service;


import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;

import java.util.List;

public interface AdminEventService {
    EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest update);

    List<EventFullDto> findEvents(List<Long> users, List<String> states, List<Long> categories,
                                  String rangeStart, String rangeEnd, Integer from, Integer size);
}
