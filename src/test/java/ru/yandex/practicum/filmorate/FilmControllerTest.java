package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private FilmController controller = new FilmController();
    private Film film;

    @Test
    void emptyNameExceptionTest() {

        film = new Film(
                "",
                "Description",
                LocalDate.of(2010, 11, 15),
                120);
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.create(film)
        );
        assertEquals("Название фильма не может быть пустым.", exception.getMessage());
    }
    @Test
    void longDescriptionExceptionTest() {
        film = new Film(
                "Film",
                "Жизнь десятилетнего Гарри Поттера нельзя назвать сладкой: родители умерли, едва ему " +
                        "исполнился год, а от дяди и тёти, взявших сироту на воспитание, достаются лишь тычки " +
                        "да подзатыльники. Но в одиннадцатый день рождения Гарри всё меняется. Странный гость, " +
                        "неожиданно появившийся на пороге, приносит письмо, из которого мальчик узнаёт, " +
                        "что на самом деле он - волшебник и зачислен в школу магии под названием Хогвартс. А уже " +
                        "через пару недель Гарри будет мчаться в поезде Хогвартс-экспресс навстречу новой жизни, " +
                        "где его ждут невероятные приключения, верные друзья и самое главное — ключ к разгадке тайны " +
                        "смерти его родителей.",
                LocalDate.of(2010, 11, 15),
                120);

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.create(film)
        );
        assertEquals("Максимальная длина описания — 200 символов.", exception.getMessage());
    }
    @Test
    void releaseDayExceptionTest() {
        film = new Film(
                "Film",
                "Description",
                LocalDate.of(1895, 12, 27),
                120);

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.create(film)
        );
        assertEquals("Дата релиза — не раньше 28 декабря 1895 года.", exception.getMessage());
    }
    @Test
    void notPositiveDurationExceptionTest() {
        film = new Film(
                "Film",
                "Description",
                LocalDate.of(2010, 11, 15),
                0);

        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.create(film)
        );
        assertEquals("Продолжительность фильма должна быть положительной.", exception.getMessage());
    }
}


