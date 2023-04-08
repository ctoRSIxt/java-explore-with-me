package ru.practicum.ewm.event.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.client.StatsClient;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.PublicEventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "events")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicEventController {

    private final StatsClient statsClient;
    private final PublicEventService publicEventService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<EventShortDto> findEvents(@RequestParam(required = false) String text,
                                          @RequestParam(required = false) List<Long> categories,
                                          @RequestParam(required = false) Boolean paid,
                                          @RequestParam(required = false) String rangeStart,
                                          @RequestParam(required = false) String rangeEnd,
                                          @RequestParam(required = false) Boolean onlyAvailable,
                                          @RequestParam(required = false) String sort,
                                          @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                          @RequestParam(defaultValue = "10") @Positive Integer size,
                                          HttpServletRequest httpServletRequest) {
        statsClient.saveEndpointHit(httpServletRequest);
        return publicEventService.findEvents(text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable,
                sort, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public EventFullDto findEventById(@PathVariable Long id,
                                      HttpServletRequest httpServletRequest) {
        statsClient.saveEndpointHit(httpServletRequest);
        return publicEventService.findEventById(id);
    }
}
