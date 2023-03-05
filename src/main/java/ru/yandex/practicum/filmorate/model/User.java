package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor

public class User {
    private Integer id;
    private final String email;
    private final String login;
    private String name;
    private final LocalDate birthday;
}
