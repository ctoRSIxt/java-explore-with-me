package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.EndpointHit;
import ru.practicum.ewm.model.ViewStats;

import java.time.LocalDateTime;


public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(" select new ru.practicum.ewm.model.ViewStats(h.app, h.uri, count(h.ip)) from EndpointHit h " +
            "where h.uri = ?1 and (h.timestamp between ?2 and ?3) " +
            "group by h.app, h.uri, h.ip")
    ViewStats findHits(String uri, LocalDateTime start, LocalDateTime end);


    @Query(" select new ru.practicum.ewm.model.ViewStats(h.app, h.uri, count(distinct h.ip)) from EndpointHit h " +
            "where h.uri = ?1 and (h.timestamp between ?2 and ?3) " +
            "group by h.app, h.uri, h.ip")
    ViewStats findUniqueHits(String uri, LocalDateTime start, LocalDateTime end);
}