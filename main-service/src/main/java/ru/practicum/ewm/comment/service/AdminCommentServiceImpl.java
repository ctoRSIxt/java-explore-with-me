package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.exception.NotFoundException;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    private final CommentRepository commentRepository;

    @Transactional
    @Override
    public void delete(Long commentId) {

        commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Comment with id=" + commentId + " was not found"));

        commentRepository.deleteById(commentId);
    }
}
