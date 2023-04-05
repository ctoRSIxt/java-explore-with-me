package ru.practicum.ewm.compilation.service;

import ru.practicum.ewm.compilation.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {

    List<CompilationDto> find(Boolean pinned, Integer from, Integer size);

    CompilationDto findById(Long compId);

}
