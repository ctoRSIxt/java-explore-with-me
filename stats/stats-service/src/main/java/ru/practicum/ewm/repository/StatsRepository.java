package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;


public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(" select new ru.practicum.ewm.model.ViewStats(h.app, h.uri, count(h.ip)) from EndpointHit h " +
            "where ((?1) is null or h.uri in (?1)) and (h.timestamp between ?2 and ?3) " +
            "group by h.app, h.uri")
    List<ViewStats> findHits(List<String> uri, LocalDateTime start, LocalDateTime end);


    @Query(" select new ru.practicum.ewm.model.ViewStats(h.app, h.uri, count(distinct h.ip)) from EndpointHit h " +
            "where ((?1) is null or h.uri in (?1)) and (h.timestamp between ?2 and ?3) " +
            "group by h.app, h.uri")
    List<ViewStats> findUniqueHits(List<String> uri, LocalDateTime start, LocalDateTime end);
}