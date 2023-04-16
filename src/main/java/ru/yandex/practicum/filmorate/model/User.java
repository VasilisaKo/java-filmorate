package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private Integer id;
    @Email(message = "Электронная почта не может быть пустой и должна содержать символ @")
    private String email;
    @NotEmpty(message = "Логин не может быть пустым")
    private String login;
    private String name;
    @NotNull(message = "Поле Дата рождения не может быть пустым")
    @Past(message = "Дата рождения не может быть в будущем")
    private LocalDate birthday;
    private Set<Integer> friends = new HashSet<>();

    public boolean addFriend(Integer friendId) {
        return friends.add(friendId);
    }

    public boolean deleteFriend(final Integer id) {
        return friends.remove(id);
    }
}

