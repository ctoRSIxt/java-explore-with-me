package ru.practicum.ewm.service;

import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    List<ViewStatsDto> find(LocalDateTime start,
                            LocalDateTime end,
                            List<String> uris,
                            boolean unique);
}
