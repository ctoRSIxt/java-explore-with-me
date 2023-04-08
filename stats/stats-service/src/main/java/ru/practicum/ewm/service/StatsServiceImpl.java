package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.model.EndpointHitMapper;
import ru.practicum.ewm.model.ViewStats;
import ru.practicum.ewm.model.ViewStatsMapper;
import ru.practicum.ewm.repository.StatsRepository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatsServiceImpl implements StatsService {
    private final StatsRepository statsRepository;

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        statsRepository.save(EndpointHitMapper.toEndpointHit(endpointHitDto));
        return endpointHitDto;
    }

    @Override
    public List<ViewStatsDto> find(LocalDateTime start,
                                   LocalDateTime end,
                                   List<String> uris,
                                   boolean unique) {

        List<ViewStats> viewStatsList;
        if (uris != null) {
            if (unique) {
                viewStatsList = statsRepository.findUniqueHits(uris, start, end);
            } else {
                viewStatsList = statsRepository.findHits(uris, start, end);
            }
        } else {
            if (unique) {
                viewStatsList = statsRepository.findUniqueHitsUriNull(start, end);
            } else {
                viewStatsList = statsRepository.findHitsUriNull(start, end);
            }
        }

        return viewStatsList.stream()
                .sorted(Comparator.comparing(ViewStats::getHits).reversed())
                .map(ViewStatsMapper::toViewStatsDto)
                .collect(Collectors.toList());
    }

}
