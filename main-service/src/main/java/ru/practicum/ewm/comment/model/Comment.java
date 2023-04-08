package ru.practicum.ewm.comment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.comment.enums.CommentState;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private LocalDateTime created;

    @ManyToOne(optional = false)
    private User author;

    @ManyToOne(optional = false)
    private Event event;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CommentState state = CommentState.NOT_EDIT;
}



























