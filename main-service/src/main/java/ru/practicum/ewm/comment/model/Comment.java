package ru.practicum.ewm.comment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(nullable = false, length = 7000)
    private String text;

    @ManyToOne(optional = false)
    private User author;

    @ManyToOne(optional = false)
    private Event event;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    private LocalDateTime editedOn;
}



























