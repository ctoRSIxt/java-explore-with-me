package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.service.PrivateCommentService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PrivateCommentController {
    private final PrivateCommentService privateCommentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events/{eventId}/comment")
    public CommentDto create(@PathVariable Long userId,
                             @PathVariable Long eventId,
                             @Valid @RequestBody NewCommentDto newCommentDto) {
        return privateCommentService.create(userId, eventId, newCommentDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/events/{eventId}/comment/{commentId}")
    public CommentDto update(@PathVariable Long userId,
                             @PathVariable Long eventId,
                             @PathVariable Long commentId,
                             @Valid @RequestBody NewCommentDto newCommentDto) {

        return privateCommentService.update(userId, eventId, commentId, newCommentDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/events/{eventId}/comment/{commentId}")
    public void delete(@PathVariable Long userId,
                       @PathVariable Long eventId,
                       @PathVariable Long commentId) {
        privateCommentService.delete(userId, eventId, commentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/events/{eventId}/comment")
    public List<CommentDto> findAllByEvent(@PathVariable Long userId,
                                           @PathVariable Long eventId,
                                           @RequestParam(defaultValue = "0")
                                           @Min(0) Integer from,
                                           @RequestParam(defaultValue = "10")
                                           @Min(1) Integer size) {
        return privateCommentService.findAllByEvent(userId, eventId, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comment")
    public List<CommentDto> findAll(@PathVariable Long userId,
                                    @RequestParam(defaultValue = "0")
                                    @Min(0) Integer from,
                                    @RequestParam(defaultValue = "10")
                                    @Min(1) Integer size) {
        return privateCommentService.findAll(userId, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/comment/{commentId}")
    public CommentDto find(@PathVariable Long userId,
                           @PathVariable Long commentId) {
        return privateCommentService.find(userId, commentId);
    }
}
