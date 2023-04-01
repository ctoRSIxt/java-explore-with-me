package ru.practicum.ewm.event.model;


import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.event.enums.State;
import ru.practicum.ewm.event.location.Location;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String annotation;

    @Column(nullable = false, length = 5000)
    private String description;

    @Embedded
    private Location location;

    @Column(nullable = false)
    private LocalDateTime eventDate;

    @Column(nullable = false)
    private Boolean paid = Boolean.FALSE;

    @ManyToOne(optional = false)
    private Category category;

    @Column(nullable = false)
    private Boolean requestModeration = Boolean.TRUE;

    @Column(nullable = false)
    private Integer participantLimit = 0;

    @Column(nullable = false)
    private Integer confirmedRequests = 0;

    @Column(nullable = false)
    private Long views = 0l;

    @ManyToOne(optional = false)
    private User initiator;

    @Column(nullable = false)
    private LocalDateTime createOn;

    @Column(nullable = false)
    private LocalDateTime publishedOn;

    @Enumerated(EnumType.STRING)
    private State state;
}
