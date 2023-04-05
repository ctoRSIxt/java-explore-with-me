package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.enums.StateAction;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.AdminEventRepository;
import ru.practicum.ewm.exception.ConditionsNotMetException;
import ru.practicum.ewm.exception.CustomValidationException;
import ru.practicum.ewm.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminEventServiceImpl implements AdminEventService {

    private final static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final AdminEventRepository eventRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminRequest update) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Event with id=" + eventId + " was not found"));


        if (update.getTitle() != null && !update.getTitle().isBlank()) {
            event.setTitle(update.getTitle());
        }

        if (update.getAnnotation() != null && !update.getAnnotation().isBlank()) {
            event.setAnnotation(update.getAnnotation());
        }

        if (update.getDescription() != null && !update.getDescription().isBlank()) {
            event.setDescription(update.getDescription());
        }

        if (update.getLocation() != null) {
            event.setLocation(update.getLocation());
        }

        if (update.getEventDate() != null) {
            if (event.getState().equals(State.PUBLISHED)) {
                if (update.getEventDate().minusHours(1).isBefore(event.getPublishedOn())) {
                    throw new ConditionsNotMetException(
                            "For the requested operation the conditions are not met.",
                            "Event should start not earlier than an hour from publication time");
                }
            }
            if (update.getEventDate().isBefore(LocalDateTime.now())) {
                throw new ConditionsNotMetException(
                        "For the requested operation the conditions are not met.",
                        "Event cannot start in the past");
            }
            event.setEventDate(update.getEventDate());
        }

        if (update.getPaid() != null) {
            event.setPaid(update.getPaid());
        }

        if (update.getCategory() != null) {
            Category category = categoryRepository.findById(update.getCategory())
                    .orElseThrow(() -> new NotFoundException("The required object was not found.",
                            "Category with id=" + update.getCategory() + " was not found"));
            event.setCategory(category);
        }

        if (update.getRequestModeration() != null) {
            event.setRequestModeration(update.getRequestModeration());
        }

        if (update.getParticipantLimit() != null) {
            event.setParticipantLimit(update.getParticipantLimit());
        }

        if (update.getStateAction() != null) {

            if (update.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                if (event.getState().equals(State.PUBLISHED)) {
                    throw new ConditionsNotMetException(
                            "For the requested operation the conditions are not met.",
                            "Cannot publish the event because it's not in the right state: PUBLISHED");
                }
                if (event.getState().equals(State.CANCELED)) {
                    throw new ConditionsNotMetException(
                            "For the requested operation the conditions are not met.",
                            "Cannot publish the event because it's not in the right state: CANCELED");
                }
                event.setPublishedOn(LocalDateTime.now());
                event.setState(State.PUBLISHED);
            }

            if (update.getStateAction().equals(StateAction.REJECT_EVENT)) {
                if (event.getState().equals(State.PUBLISHED)) {
                    throw new ConditionsNotMetException(
                            "Cannot reject the event because it's not in the right state: PUBLISHED",
                            "For the requested operation the conditions are not met.");
                }
                event.setState(State.CANCELED);
            }
        }


        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> findEvents(List<Long> users, List<String> states, List<Long> categories,
                                         String rangeStart, String rangeEnd, Integer from, Integer size) {


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


        Page<Event> events = eventRepository.findByParamsAdmin(
                users, State.validateState(states), categories, start, end,
                PageRequest.of(from / size, size, Sort.by("id").descending()));

        return events.stream()
                .map(EventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }
}
