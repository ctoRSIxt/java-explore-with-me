package ru.practicum.ewm.compilation.mapper;

import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;

import java.util.List;
import java.util.stream.Collectors;

public class CompilationMapper {

    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                compilation.getEvents().stream()
                                .map(EventMapper::toEventShortDto)
                                .collect(Collectors.toList()));
    }
}
