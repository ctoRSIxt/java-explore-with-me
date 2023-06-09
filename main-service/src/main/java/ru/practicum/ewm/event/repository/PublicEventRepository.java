package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PublicEventRepository extends JpaRepository<Event, Long> {

    @Query(" select e from Event e " +
            "where e.state = ru.practicum.ewm.event.enums.State.PUBLISHED " +
            "and (:categories is null or (e.category.id in :categories)) " +
            "and (:paid is null or (e.paid = :paid)) " +
            "and (:onlyAvailable is null or ((:onlyAvailable = true and e.confirmedRequests < e.participantLimit) " +
            "or (:onlyAvailable = false and 1 = 1))) " +
            "and (:text is null or ((lower(e.description) like lower(:text)) " +
            "or (lower(e.annotation) like lower(:text)))) " +
            "and (cast(:rangeStart as date) is null or e.eventDate >= :rangeStart) " +
            "and (cast(:rangeEnd as date) is null or e.eventDate <= :rangeEnd) ")
    Page<Event> findByParamsPublic(@Param("text") String text,
                                   @Param("categories") List<Long> categories,
                                   @Param("paid") Boolean paid,
                                   @Param("rangeStart") LocalDateTime rangeStart,
                                   @Param("rangeEnd") LocalDateTime rangeEnd,
                                   @Param("onlyAvailable") Boolean onlyAvailable,
                                   Pageable pageable);

    Optional<Event> findByIdAndState(Long id, State state);
}
