package ru.practicum.ewm.event.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.RequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PrivateEventController {
    private final EventService eventService;

    @PostMapping("{userId}/events")
    public EventShortDto create(@PathVariable @Positive Long userId,
                                @Valid @RequestBody NewEventDto newEventDto) {
        return eventService.create(userId, newEventDto);
    }

    @GetMapping("{userId}/events")
    public List<EventShortDto> findAllUserEvents(@PathVariable @Positive Long userId,
                                                 @RequestParam(defaultValue = "0")
                                                 @Min(0) Integer from,
                                                 @RequestParam(defaultValue = "10")
                                                 @Positive Integer size) {
        return eventService.findUserEvents(userId, from, size);
    }

    @GetMapping("{userId}/events/{eventId}")
    public EventFullDto findUserEvent(@PathVariable @Positive Long userId,
                                      @PathVariable @Positive Long eventId) {
        return eventService.findUserEvent(userId, eventId);
    }

    @PatchMapping("{userId}/events/{eventId}")
    public updateEvent(@PathVariable @Positive Long userId,
                       @PathVariable @Positive Long eventId,
                       @Valid @RequestBody NewEventDto newEventDto) {

        return eventService.updateEvent(userId, eventId, newEventDto);
    }

    @GetMapping("{userId}/events/{eventId}/requests")
    public List<RequestDto> findUserRequests(@PathVariable @Positive Long userId,
                                             @PathVariable @Positive Long eventId) {
        return eventService.findUserRequests(userId, eventId);
    }

    @PatchMapping("{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResult changeRequestStatus(@PathVariable @Positive Long userId,
                                                              @PathVariable @Positive Long eventId,
                                                              @RequestBody EventRequestStatusUpdateRequest
                                                                          updateRequestStatus) {
        return eventService.changeRequestStatus(userId, eventId, updateRequestStatus);
    }
}
