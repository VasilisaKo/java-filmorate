package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    @NotEmpty(message = "Название фильма не может быть пустым")
    private final String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private final String description;
    @NotNull(message = "Дата релиза не может быть пустой")
    private final LocalDate releaseDate;
    @Min(value = 0, message = "Продолжительность фильма должна быть положительной")
    private final int duration;
    private final Set<Integer> likes = new HashSet<>();

    public void addLikes(Integer idFriend) {
        likes.add(idFriend);
    }

    public Integer countLikes() {
        return likes.size();
    }
}


