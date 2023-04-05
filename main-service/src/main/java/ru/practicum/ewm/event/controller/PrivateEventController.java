package ru.practicum.ewm.event.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.service.PrivateEventService;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.RequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PrivateEventController {
    private final PrivateEventService privateEventService;

    @PostMapping
    public EventShortDto create(@PathVariable Long userId,
                                @Valid @RequestBody NewEventDto newEventDto) {
        return privateEventService.create(userId, newEventDto);
    }

    @GetMapping
    public List<EventShortDto> findAll(@PathVariable Long userId,
                                       @RequestParam(defaultValue = "0")
                                       @Min(0) Integer from,
                                       @RequestParam(defaultValue = "10")
                                       @Min(1) Integer size) {
        return privateEventService.findAll(userId, from, size);
    }

    @GetMapping("/{eventId}")
    public EventFullDto find(@PathVariable Long userId,
                             @PathVariable Long eventId) {
        return privateEventService.find(userId, eventId);
    }

    @PatchMapping("/{eventId}")
    public EventFullDto updateEvent(@PathVariable Long userId,
                                    @PathVariable Long eventId,
                                    @Valid @RequestBody UpdateEventUserRequest newEventDto) {

        return privateEventService.update(userId, eventId, newEventDto);
    }

    @GetMapping("/{eventId}/requests")
    public List<RequestDto> findRequests(@PathVariable Long userId,
                                         @PathVariable Long eventId) {
        return privateEventService.findRequests(userId, eventId);
    }

    @PatchMapping("/{eventId}/requests")
    public EventRequestStatusUpdateResult updateRequestStatus(@PathVariable @Positive Long userId,
                                                              @PathVariable @Positive Long eventId,
                                                              @RequestBody EventRequestStatusUpdateRequest
                                                                      requestStatusUpdateRequest) {
        return privateEventService.updateRequestStatus(userId, eventId, requestStatusUpdateRequest);
    }
}
