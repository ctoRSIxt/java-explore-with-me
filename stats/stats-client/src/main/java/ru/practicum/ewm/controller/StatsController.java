package ru.practicum.ewm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatsController {
    private final StatsClient statsClient;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/hit")
    public ResponseEntity<Object> create(@RequestBody EndpointHitDto endpointHitDto) {
        return statsClient.create(endpointHitDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/stats")
    public ResponseEntity<Object> find(@RequestParam LocalDateTime start,
                                       @RequestParam LocalDateTime end,
                                       @RequestParam(required = false) List<String> uris,
                                       @RequestParam(defaultValue = "false") boolean unique) {
        return statsClient.find(start, end, uris, unique);
    }
}