package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService{

    @Transactional
    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto NewCommentDto) {

    }

    @Transactional
    @Override
    public CommentDto update(Long userId, Long eventId, Long commentId, NewCommentDto NewCommentDto) {

    }

    @Transactional
    @Override
    public void delete(Long userId, Long eventId, Long commentId) {

    }

    @Override
    public List<CommentDto> findAllByEvent(Long userId, Long eventId) {

    }

    @Override
    public List<CommentDto> findAll(Long userId, Integer from, Integer size) {

    }

    @Override
    public CommentDto find(Long userId, Long commentId) {
        
    }
}
