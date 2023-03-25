package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final int duration;
    private final Set<Integer> likes = new HashSet<>();
    public void addLikes(Integer idFriend) {
        likes.add(idFriend);
    }
    public Integer countLikes() {
        return likes.size();
    }
}


