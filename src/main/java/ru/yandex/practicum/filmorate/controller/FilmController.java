package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage,
                          FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> findAll() {
        return filmStorage.findAll();
    }

    @GetMapping("/{id}")
    public Film getFilmsById(@PathVariable(required = false, name = "id") Integer id) {
        return filmStorage.getFilmById(id);
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmStorage.create(film);

    }

    @PutMapping
    public Film update(@RequestBody Film film) {
        return filmStorage.update(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLikeToFilm(@PathVariable("id") Integer filmId,
                              @PathVariable("userId") Integer userId) {
        return filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLikeToFilm(@PathVariable("id") Integer filmId,
                                 @PathVariable("userId") Integer userId) {
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getFilmsByCount(
            @RequestParam(value = "count", defaultValue = "10", required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }
}
