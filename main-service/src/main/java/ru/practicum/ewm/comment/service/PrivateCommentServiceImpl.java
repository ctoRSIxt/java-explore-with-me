package ru.practicum.ewm.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.comment.dto.CommentDto;
import ru.practicum.ewm.comment.dto.NewCommentDto;
import ru.practicum.ewm.comment.model.Comment;
import ru.practicum.ewm.comment.repository.CommentRepository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.repository.PrivateEventRepository;
import ru.practicum.ewm.exception.ConditionsNotMetException;
import ru.practicum.ewm.exception.NotFoundException;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.PrivateUserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PrivateCommentServiceImpl implements PrivateCommentService {

    private final CommentRepository commentRepository;
    private final PrivateEventRepository eventRepository;
    private final PrivateUserRepository userRepository;

    @Transactional
    @Override
    public CommentDto create(Long userId, Long eventId, NewCommentDto newCommentDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "User with id=" + userId + " was not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Event with id=" + eventId + " was not found"));


        Comment comment = new Comment();
        comment.setText(newCommentDto.getText());
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreatedOn(LocalDateTime.now());

        return CommentDto.toCommentDto(commentRepository.save(comment));
    }

    @Transactional
    @Override
    public CommentDto update(Long userId, Long eventId, Long commentId, NewCommentDto newCommentDto) {

        Comment comment = getComment(userId, eventId, commentId);

        comment.setText(newCommentDto.getText());
        comment.setEditedOn(LocalDateTime.now());

        return CommentDto.toCommentDto(commentRepository.save(comment));

    }

    @Transactional
    @Override
    public void delete(Long userId, Long eventId, Long commentId) {
        getComment(userId, eventId, commentId);
        commentRepository.deleteById(commentId);
    }

    @Override
    public List<CommentDto> findAllByEvent(Long userId, Long eventId, Integer from, Integer size) {
        return commentRepository.findAllByAuthorIdAndEventId(userId, eventId,
                        PageRequest.of(from / size, size, Sort.by("createdOn").descending()))
                .stream()
                .map(CommentDto::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findAll(Long userId, Integer from, Integer size) {

        return commentRepository.findAllByAuthorId(userId,
                        PageRequest.of(from / size, size, Sort.by("createdOn").descending()))
                .stream()
                .map(CommentDto::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto find(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Comment with id=" + commentId + " was not found"));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Comment doesn't belong to user with id=" + userId);
        }

        return CommentDto.toCommentDto(comment);
    }


    private Comment getComment(Long userId, Long eventId, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("The required object was not found.",
                        "Comment with id=" + commentId + " was not found"));

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Comment doesn't belong to user with id=" + userId);
        }

        if (!comment.getEvent().getId().equals(eventId)) {
            throw new ConditionsNotMetException("Incorrectly made request.",
                    "Comment isn't related to event with id=" + eventId);
        }
        return comment;
    }
}
