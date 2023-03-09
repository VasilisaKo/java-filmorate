package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int idGenerator = 1;

    @GetMapping
    public Collection<Film> findAll() {
        log.debug("Текущее количество фильмов: {}", films.size());
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        if(film.getName() == null || film.getName().isEmpty()) {
            log.error("Название фильма не может быть пустым.");
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if(film.getDescription().length() > 200) {
            log.error("Максимальная длина описания — 200 символов.");
            throw new ValidationException("Максимальная длина описания — 200 символов.");
        }
        if(film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))) {
            log.error("Дата релиза — не раньше 28 декабря 1895 года.");
            throw new ValidationException("Дата релиза — не раньше 28 декабря 1895 года.");
        }
        if(film.getDuration() <= 0) {
            log.error("Продолжительность фильма должна быть положительной.");
            throw new ValidationException("Продолжительность фильма должна быть положительной.");
        }
        film.setId(idGenerator++);
        films.put(film.getId(), film);
        log.debug("Добавлен новый фильм: {}", film);
        return film;
    }

    @PutMapping
    public Film put(@RequestBody Film film) {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.debug("Фильм обновлен: {}", film);
            return film;
        }else {
            log.error("Фильм с id {} не найден", film.getId());
            throw new ValidationException("Фильм с id " + film.getId() + "не найден");
        }
    }
}
