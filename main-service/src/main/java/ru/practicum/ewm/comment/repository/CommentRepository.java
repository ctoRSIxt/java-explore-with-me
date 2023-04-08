package ru.practicum.ewm.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findAllByUserIdAndEventId(Long userId, Long eventId, Pageable pageable);

    Page<Comment> findAllByUserId(Long userId, Pageable pageable);

}
