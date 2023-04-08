package ru.practicum.ewm.comment.service;

import ru.practicum.ewm.category.dto.CategoryDto;

import java.util.List;

public interface PublicCommentService {

    List<CategoryDto> find(String text, List<Long> authors, List<Long> events,
                           String rangeStart, String rangeEnd, String sort,
                           Integer from, Integer size);

    CategoryDto findById(Long commentId);
}
