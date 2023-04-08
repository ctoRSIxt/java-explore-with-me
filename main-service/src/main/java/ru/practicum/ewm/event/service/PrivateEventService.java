package ru.practicum.ewm.event.service;

import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.RequestDto;

import java.util.List;

public interface PrivateEventService {
    EventFullDto create(Long userId, NewEventDto newEventDto);

    List<EventShortDto> findAll(Long userId, Integer from, Integer size);

    EventFullDto find(Long userId, Long eventId);

    EventFullDto update(Long userId, Long eventId, UpdateEventUserRequest newEventDto);

    List<RequestDto> findRequests(Long userId, Long eventId);

    EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId,
                                                       EventRequestStatusUpdateRequest requestStatusUpdateRequest);

}
