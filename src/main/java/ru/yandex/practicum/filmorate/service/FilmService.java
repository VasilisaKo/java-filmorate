package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }
    public Film addLike(Integer filmId, Integer userId) {
        if (filmId < 0 || userId < 0) {
            log.error("Передан отрицатальный id");
            throw new NotFoundException("Передан отрицатальный id");
        }
        Film film = filmStorage.getFilmById(filmId);
        film.addLikes(userId);
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) {
        if (filmId < 0 || userId < 0) {
            log.error("Передан отрицатальный id");
            throw new NotFoundException("Передан отрицатальный id");
        }
        Film film = filmStorage.getFilmById(filmId);
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAll().stream()
                .sorted(Comparator.comparing(Film::countLikes).reversed())
                .limit(count).collect(Collectors.toList());
    }


}
