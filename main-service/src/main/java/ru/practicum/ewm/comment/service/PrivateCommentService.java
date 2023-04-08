package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;

import java.util.List;

public interface PrivateCommentService {

    CommentDto create(Long userId, Long eventId, NewCommentDto NewCommentDto);

    CommentDto update(Long userId, Long eventId, Long commentId, NewCommentDto NewCommentDto);

    void delete(Long userId, Long eventId, Long commentId);

    List<CommentDto> findAllByEvent(Long userId, Long eventId);

    List<CommentDto> findAll(Long userId, Integer from, Integer size);

    CommentDto find(Long userId, Long commentId);
}
