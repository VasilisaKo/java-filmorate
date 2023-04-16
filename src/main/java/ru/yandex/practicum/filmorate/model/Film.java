package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotEmpty(message = "Название фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Максимальная длина описания — 200 символов")
    private String description;
    @NotNull(message = "Дата релиза не может быть пустой")
    private LocalDate releaseDate;
    @Min(value = 0, message = "Продолжительность фильма должна быть положительной")
    private int duration;
    private int rate;
    @NotNull
    private Mpa mpa;
    private List<Genre> genres = new ArrayList<>();
    private Set<Integer> likes = new HashSet<>();

    public void addLikes(Integer idFriend) {
        likes.add(idFriend);
    }
    public boolean deleteLikes(Integer userId) {
        return likes.remove(userId);
    }

    public Integer countLikes() {
        return likes.size();
    }
}


