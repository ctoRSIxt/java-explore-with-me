package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.PublicEventRepository;
import ru.practicum.ewm.exception.CustomValidationException;
import ru.practicum.ewm.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final PublicEventRepository eventRepository;

    @Override
    public List<EventShortDto> findEvents(String text, List<Long> categories, Boolean paid,
                                          String rangeStart, String rangeEnd, Boolean onlyAvailable,
                                          String sort, Integer from, Integer size) {

        LocalDateTime start = null;
        if (rangeStart != null) {
            start = LocalDateTime.parse(rangeStart, TIME_FORMATTER);
        }

        LocalDateTime end = null;
        if (rangeEnd != null) {
            end = LocalDateTime.parse(rangeEnd, TIME_FORMATTER);
        }

        if (start != null && end != null) {
            if (start.isAfter(end)) {
                throw new CustomValidationException(
                        "rangeStart is before rangeEnd",
                        "Incorrectly made request.");
            }
        }


        PageRequest pageRequest = null;
        if (sort != null) {
            switch (sort) {
                case "EVENT_DATE":
                    pageRequest = PageRequest.of(from / size, size, Sort.by("eventDate").descending());
                    break;

                case "VIEWS":
                    pageRequest = PageRequest.of(from / size, size, Sort.by("views").descending());
                    break;
                default:
                    pageRequest = PageRequest.of(from / size, size, Sort.by("id").descending());
            }
        } else {
            pageRequest = PageRequest.of(from / size, size, Sort.by("id").descending());
        }

        Page<Event> events = eventRepository.findByParamsPublic(text, categories, paid, start, end,
                onlyAvailable, pageRequest);

        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventFullDto findEventById(Long id) {

        Event event = eventRepository.findByIdAndState(id, State.PUBLISHED)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Published event with id=" + id + " was not found"));

        return EventMapper.toEventFullDto(event);
    }
}
