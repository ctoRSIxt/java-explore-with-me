package ru.practicum.ewm.compilation.service;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto create(NewCompilationDto newCompilationDto);

    CompilationDto update(Long compId, NewCompilationDto newCompilationDto);

    void delete(Long compId);
}
