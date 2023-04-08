package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @")
    private final String email;
    @NotEmpty(message = "Логин не может быть пустым")
    private final String login;
    private String name;
    @NotNull(message = "Поле Дата рождения не может быть пустым")
    @Past(message = "Дата рождения не может быть в будущем")
    private final LocalDate birthday;
    private final Set<Integer> friends = new HashSet<>();

    public void setFriends(Integer friendId) {
        friends.add(friendId);
    }
}
