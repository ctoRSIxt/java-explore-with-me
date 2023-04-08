package ru.practicum.ewm.event.location;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Location {
    private Float lat;
    private Float lon;
}
