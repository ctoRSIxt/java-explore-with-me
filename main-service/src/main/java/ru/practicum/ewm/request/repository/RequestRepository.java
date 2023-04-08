package ru.practicum.ewm.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.request.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findAllByEventId(Long eventId);

    List<Request> findAllByIdIn(List<Long> id);

    List<Request> findAllByRequesterId(Long userId);

    List<Request> findAllByEventIdAndRequesterId(Long eventId, Long userId);

    Optional<Request> findByIdAndRequesterId(Long id, Long userId);
}
