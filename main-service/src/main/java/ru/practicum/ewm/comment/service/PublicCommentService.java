package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;

import java.util.List;

public interface PublicCommentService {

    List<CommentDto> find(String text, List<Long> authors, List<Long> events,
                          String rangeStart, String rangeEnd, String sort,
                          Integer from, Integer size);

    CommentDto findById(Long commentId);
}
