package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AdminCommentServiceImpl implements AdminCommentService {

    @Transactional
    @Override
    public void delete(Long commentId) {

    }
}
