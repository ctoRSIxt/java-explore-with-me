package ru.practicum.ewm.request.mapper;

import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.model.Request;

public class RequestMapper {

    public static RequestDto toRequestDto(Request request) {
        return new RequestDto(request.getId(),
                request.getRequester().getId(),
                request.getEvent().getId(),
                request.getCreated(),
                request.getStatus());
    }
}
