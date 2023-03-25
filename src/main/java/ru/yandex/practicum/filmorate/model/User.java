package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private final Set<Integer> friends = new HashSet<>();
    public void setFriends(Integer friendId) {
        friends.add(friendId);
    }
}
