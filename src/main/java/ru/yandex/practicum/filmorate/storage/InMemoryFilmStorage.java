package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @Override
    public Film addFilm(Film film) {
        film.setId(filmId++);
        films.put(film.getId(), film);
        log.debug("Добавлен новый фильм: {}", film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Фильм обновлен: {}", film);
            return film;
        } else {
            log.error("Фильм с id {} не найден", film.getId());
            throw new IncorrectParameterException("Фильм с id " + film.getId() + "не найден");
        }
    }

    @Override
    public List<Film> getFilmsList() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        log.debug("Фильм c id" + id + "не найден");
        throw new NotFoundException("Фильм c id" + id + "не найден");
    }

    @Override
    public void addLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getLikes().add(userId);
        updateFilm(film);

    }

    @Override
    public void removeLike(int filmId, int userId) {
        Film film = films.get(filmId);
        film.getLikes().remove(userId);
        updateFilm(film);
    }
}
