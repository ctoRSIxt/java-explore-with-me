package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.EventRepository;
import ru.practicum.ewm.exception.ConditionsNotMetException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.request.dto.RequestDto;
import ru.practicum.ewm.request.mapper.RequestMapper;
import ru.practicum.ewm.request.model.Request;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.AdminUserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PrivateRequestServiceImpl implements PrivateRequestService {

    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;
    private final AdminUserRepository adminUserRepository;

    @Transactional
    @Override
    public RequestDto create(Long userId, Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Event with id=" + eventId + " was not found"));

        if (!requestRepository.findAllByEventIdAndRequesterId(eventId, userId).isEmpty()) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Request from user with id=" + userId + " for event with id=" + eventId
                            + " already exists");
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Initiator cannot request for its own event");
        }

        if (!event.getState().equals(State.PUBLISHED)) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Event with id=" + eventId + " is not published");
        }

        if (event.getParticipantLimit().equals(event.getConfirmedRequests())) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Event reached the participation limit");
        }

        Request request = new Request();

        User user = adminUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "User with id=" + userId + " was not found"));

        request.setRequester(user);
        request.setEvent(event);

        if (event.getParticipantLimit() == 0 ||
                (event.getParticipantLimit() > event.getConfirmedRequests())) {
            if (!event.getRequestModeration()) {
                request.setStatus(RequestStatus.CONFIRMED);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
                eventRepository.save(event);
            } else {
                request.setStatus(RequestStatus.PENDING);
            }
        } else {
            throw new ConditionsNotMetException(
                    "For the requested operation the conditions are not met.",
                    "All places are taken");
        }

        return RequestMapper.toRequestDto(requestRepository.save(request));
    }

    @Override
    public List<RequestDto> findAll(Long userId) {
        return requestRepository.findAllByRequesterId(userId).stream()
                .map(RequestMapper::toRequestDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public RequestDto cancelRequest(Long userId, Long requestId) {

        Request request = requestRepository.findByIdAndRequesterId(requestId, userId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Request with id=" + requestId + " for user with id=" + userId
                                + " was not found"));

        Event event = request.getEvent();

        if (request.getStatus().equals(RequestStatus.CONFIRMED)) {
            event.setConfirmedRequests(event.getConfirmedRequests() - 1);
            eventRepository.save(event);
        }
        request.setStatus(RequestStatus.CANCELED);
        return RequestMapper.toRequestDto(requestRepository.save(request));
    }
}
