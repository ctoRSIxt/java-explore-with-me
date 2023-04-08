package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.exception.CustomValidationException;
import ru.practicum.ewm.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PublicCommentServiceImpl implements PublicCommentService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private EntityManager entityManager;
    private final CommentRepository commentRepository;

    @Override
    public List<CommentDto> find(String text, List<Long> authors, List<Long> events,
                           String rangeStart, String rangeEnd, String sort,
                           Integer from, Integer size) {


        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> criteriaQuery = criteriaBuilder.createQuery(Comment.class);
        Root<Comment> commentRoot = criteriaQuery.from(Comment.class);
        Predicate predicates = criteriaBuilder.conjunction();

        if (text != null && !text.isBlank()) {
            Predicate textPredicate = criteriaBuilder.like(criteriaBuilder.lower(commentRoot.get("text")),
                    "%" + text.strip().toLowerCase() + "%");
            predicates = criteriaBuilder.and(predicates, textPredicate);
        }

        if (authors != null && !authors.isEmpty()) {
            Predicate inAuthors = commentRoot.get("author").get("id").in(authors);
            predicates = criteriaBuilder.and(predicates, inAuthors);
        }

        if (events != null && !events.isEmpty()) {
            Predicate inEvents = commentRoot.get("event").get("id").in(events);
            predicates = criteriaBuilder.and(predicates, inEvents);
        }

        if (rangeStart != null) {
            LocalDateTime start = LocalDateTime.parse(rangeStart, TIME_FORMATTER);
            Predicate startPredicate = criteriaBuilder.greaterThanOrEqualTo(commentRoot.get("createdOn"),
                    start);
            predicates = criteriaBuilder.and(predicates, startPredicate);
        }

        if (rangeEnd != null) {
            LocalDateTime end = LocalDateTime.parse(rangeEnd, TIME_FORMATTER);
            Predicate endPredicate = criteriaBuilder.lessThanOrEqualTo(commentRoot.get("createdOn"),
                    end);
            predicates = criteriaBuilder.and(predicates, endPredicate);
        }

        if (sort != null) {
            switch (sort) {
                case "AUTHORS":
                    criteriaQuery.select(commentRoot)
                            .where(predicates)
                            .orderBy(criteriaBuilder.asc(commentRoot.get("author").get("id")),
                                     criteriaBuilder.desc(commentRoot.get("event").get("id")),
                                     criteriaBuilder.desc(commentRoot.get("createdOn")));
                    break;

                case "EVENTS":
                    criteriaQuery.select(commentRoot)
                            .where(predicates)
                            .orderBy(criteriaBuilder.desc(commentRoot.get("event").get("id")),
                                     criteriaBuilder.asc(commentRoot.get("author").get("id")),
                                     criteriaBuilder.desc(commentRoot.get("createdOn")));
                    break;
                default:
                    throw new CustomValidationException("Incorrectly made request.",
                            "sort field can be AUTHORS or EVENTS. Default sort is by creation time");
            }
        } else {
            criteriaQuery.select(commentRoot)
                    .where(predicates)
                    .orderBy(criteriaBuilder.desc(commentRoot.get("createdOn")),
                             criteriaBuilder.desc(commentRoot.get("event").get("id")),
                             criteriaBuilder.asc(commentRoot.get("author").get("id")));
        }

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(from * size)
                .setMaxResults(size)
                .getResultList().stream()
                .map(CommentDto::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto findById(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Comment with id=" + commentId + " was not found"));

        return CommentDto.toCommentDto(comment);
    }
}
