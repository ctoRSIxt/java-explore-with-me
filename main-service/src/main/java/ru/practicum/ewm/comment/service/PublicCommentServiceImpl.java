package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService{

    @Override
    public List<CategoryDto> find(String text, List<Long> authors, List<Long> events,
                           String rangeStart, String rangeEnd, String sort,
                           Integer from, Integer size) {

    }

    @Override
    public CategoryDto findById(Long commentId) {

    }
}
