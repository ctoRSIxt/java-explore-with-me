package ru.practicum.ewm.event.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.event.dto.EventFullDto;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.dto.NewEventDto;
import ru.practicum.ewm.event.dto.UpdateEventUserRequest;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.enums.StateAction;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.PrivateEventRepository;
import ru.practicum.ewm.exception.ConditionsNotMetException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateRequest;
import ru.practicum.ewm.request.dto.EventRequestStatusUpdateResult;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.PrivateUserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrivateEventServiceImpl implements PrivateEventService {

    private final PrivateEventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final PrivateUserRepository privateUserRepository;
    private final RequestRepository requestRepository;

    @Override
    public EventFullDto create(Long userId, NewEventDto newEventDto) {

        Event event = new Event();

        event.setTitle(newEventDto.getTitle());
        event.setAnnotation(newEventDto.getAnnotation());
        event.setDescription(newEventDto.getDescription());
        event.setLocation(newEventDto.getLocation());

        if (!newEventDto.getEventDate().isAfter(LocalDateTime.now().plusHours(2))) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Event date and time should be at least 2 hours from now");
        }

        event.setEventDate(newEventDto.getEventDate());
        event.setPaid(newEventDto.getPaid());

        Category category = categoryRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Category with id=" + newEventDto.getCategory() + " was not found"));
        event.setCategory(category);

        event.setCategory(category);
        event.setRequestModeration(newEventDto.getRequestModeration());
        event.setParticipantLimit(newEventDto.getParticipantLimit());

        User user = privateUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "User with id=" + newEventDto.getCategory() + " was not found"));

        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(State.PENDING);

        return EventMapper.toEventFullDto(eventRepository.save(event));

    }

    @Override
    public List<EventShortDto> findAll(Long userId, Integer from, Integer size) {

        Page<Event> events = eventRepository.findAllByInitiatorId(userId,
                PageRequest.of(from / size, size,
                        Sort.by("id").descending()));

        return events.stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    private Event findEvent(Long userId, Long eventId) {

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Event with id=" + eventId + " was not found"));

        if (!event.getInitiator().getId().equals(userId)) {
            new NotFoundException("The required object was not found.",
                    "Event with id=" + eventId + " and created by" +
                            "user with id=" + userId + " was not found");
        }
        return event;
    }

    @Override
    public EventFullDto find(Long userId, Long eventId) {
        return EventMapper.toEventFullDto(findEvent(userId, eventId));
    }

    @Override
    public EventFullDto update(Long userId, Long eventId,
                               UpdateEventUserRequest update) {
        Event event = findEvent(userId, eventId);

        if (event.getState().equals(State.PUBLISHED)) {
            throw new ConditionsNotMetException(
                    "For the requested operation the conditions are not met.",
                    "Cannot update the event because it's not in the right state: PUBLISHED");
        }

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

            if (update.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
                throw new ConditionsNotMetException("Incorrectly made request.",
                        "Event date and time should be at least 2 hours from now");
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

            if (update.getStateAction().equals(StateAction.CANCEL_REVIEW)) {
                event.setState(State.CANCELED);
            }

            if (update.getStateAction().equals(StateAction.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            }
        }

        return EventMapper.toEventFullDto(eventRepository.save(event));
    }

    @Override
    public List<RequestDto> findRequests(Long userId, Long eventId) {

        Event event = findEvent(userId, eventId);
        return requestRepository.findAllByEventId(eventId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventRequestStatusUpdateResult updateRequestStatus(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest requestStatusUpdateRequest) {
        Event event = findEvent(userId, eventId);

        if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
            throw new ConditionsNotMetException("For the requested operation the conditions are not met.",
                    "The participant limit has been reached");
        }


        List<RequestDto> confirmedRequests = new ArrayList<>();
        List<RequestDto> rejectedRequests = new ArrayList<>();

        List<Request> requestList = requestRepository
                .findAllByIdIn(requestStatusUpdateRequest.getRequestIds());

        for (Request request : requestList) {

            if (request.getStatus().equals(RequestStatus.PENDING)) {
                if (event.getParticipantLimit() == 0) {
                    request.setStatus(RequestStatus.CONFIRMED);
                    event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                } else if (event.getParticipantLimit() > event.getConfirmedRequests()) {
                    if (!event.getRequestModeration()) {
                        request.setStatus(RequestStatus.CONFIRMED);
                        event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                    } else {
                        if (requestStatusUpdateRequest.getStatus().equals(RequestStatus.CONFIRMED)) {
                            request.setStatus(RequestStatus.CONFIRMED);
                            event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                        } else {
                            request.setStatus(RequestStatus.REJECTED);
                        }
                    }
                } else {
                    request.setStatus(RequestStatus.REJECTED);
                }
            } else {
                throw new ConditionsNotMetException("For the requested operation the conditions are not met.",
                        "Only PENDING requests can be confirmed");
            }

            if (request.getStatus().equals((RequestStatus.CONFIRMED))) {
                confirmedRequests.add(RequestMapper.toRequestDto(request));
            } else {
                rejectedRequests.add(RequestMapper.toRequestDto(request));
            }
        }

        eventRepository.save(event);
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }
}
