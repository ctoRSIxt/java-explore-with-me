package ru.practicum.ewm.model;

import ru.practicum.ewm.dto.ViewStatsDto;

public class ViewStatsMapper {
    public static ViewStats toViewStats(ViewStatsDto viewStatsDto) {
        return new ViewStats(viewStatsDto.getApp(),
                viewStatsDto.getUri(),
                viewStatsDto.getHits());
    }

    public static ViewStatsDto toViewStatsDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getApp(),
                viewStats.getUri(),
                viewStats.getHits());
    }
}
