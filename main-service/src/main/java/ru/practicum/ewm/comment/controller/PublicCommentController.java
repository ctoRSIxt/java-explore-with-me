package ru.practicum.ewm.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.service.PublicCommentService;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.util.List;


@RestController
@RequestMapping(path = "/comments")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PublicCommentController {
    private final PublicCommentService publicCommentService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CommentDto> find(@RequestParam(required = false) String text,
                                 @RequestParam(required = false) List<Long> authors,
                                 @RequestParam(required = false) List<Long> events,
                                 @RequestParam(required = false) String rangeStart,
                                 @RequestParam(required = false) String rangeEnd,
                                 @RequestParam(required = false) String sort,
                                 @RequestParam(defaultValue = "0") @Min(0) Integer from,
                                 @RequestParam(defaultValue = "10") @Positive Integer size) {
        return publicCommentService.find(text, authors, events, rangeStart, rangeEnd, sort, from, size);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{commentId}")
    public CommentDto findById(@PathVariable Long commentId) {
        return publicCommentService.findById(commentId);
    }
}