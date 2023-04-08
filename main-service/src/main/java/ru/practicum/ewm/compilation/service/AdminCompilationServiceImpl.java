package ru.practicum.ewm.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.dto.UpdateCompilationDto;
import ru.practicum.ewm.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.compilation.repository.CompilationRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.NotFoundException;

import java.util.HashSet;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public CompilationDto create(NewCompilationDto newCompilationDto) {

        Compilation compilation = new Compilation();

        compilation.setTitle(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.getPinned());

        if (!newCompilationDto.getEvents().isEmpty()) {
            List<Event> eventList = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
            compilation.setEvents(new HashSet<Event>(eventList));
        }

        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public CompilationDto update(Long compId, UpdateCompilationDto newCompilationDto) {
        Compilation compilation = findCompilation(compId);

        if (newCompilationDto.getTitle() != null && !newCompilationDto.getTitle().isBlank()) {
            compilation.setTitle(newCompilationDto.getTitle());
        }

        if (newCompilationDto.getPinned() != null) {
            compilation.setPinned(newCompilationDto.getPinned());
        }

        if (newCompilationDto.getEvents() != null && !newCompilationDto.getEvents().isEmpty()) {
            List<Event> eventList = eventRepository.findAllByIdIn(newCompilationDto.getEvents());
            compilation.setEvents(new HashSet<Event>(eventList));
        }

        return CompilationMapper.toCompilationDto(compilationRepository.save(compilation));
    }

    @Transactional
    @Override
    public void delete(Long compId) {
        findCompilation(compId);
        compilationRepository.deleteById(compId);
    }

    private Compilation findCompilation(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Compilation with id=" + compId + " was not found"));
    }
}
