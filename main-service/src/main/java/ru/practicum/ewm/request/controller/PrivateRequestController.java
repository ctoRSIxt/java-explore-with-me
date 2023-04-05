package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.service.PrivateRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PrivateRequestController {
    private final PrivateRequestService privateRequestService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public RequestDto create(@PathVariable Long userId,
                             @RequestParam Long eventId) {
        return privateRequestService.create(userId, eventId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<RequestDto> findAll(@PathVariable Long userId) {
        return privateRequestService.findAll(userId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{requestId}/cancel")
    public RequestDto cancelRequest(@PathVariable Long userId,
                                    @PathVariable Long requestId) {
        return privateRequestService.cancelRequest(userId, requestId);
    }
}
