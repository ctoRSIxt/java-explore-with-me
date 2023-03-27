package ru.practicum.ewm.model;

import ru.practicum.ewm.dto.ViewStatsDto;

public class ViewStatsMapper {

//    private String app;
//    private String uri;
//    private Long hits;
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
