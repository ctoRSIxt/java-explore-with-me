package ru.practicum.ewm.request.service;

import ru.practicum.ewm.request.dto.RequestDto;

import java.util.List;

public interface PrivateRequestService {

    RequestDto create(Long userId, Long eventId);

    List<RequestDto> findAll(Long userId);

    RequestDto cancelRequest(Long userId, Long requestId);
}
