package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private UserController controller = new UserController();
    private User user;
    @Test
    void emptyOrIncorrectEmailExceptionTest() {
        user = new User(
                1,
                "vasilisagmail.com",
                "vasilisa",
                "Vasilisa",
                LocalDate.of(1986, 10, 20));
        var exception = assertThrows(
                ValidationException.class,
                () -> controller.create(user)
        );
        assertEquals("Электронная почта не может быть пустой и должна содержать символ @.", exception.getMessage());
    }
    @Test
    void emptyOrContainSpaceLoginExceptionTest() {
        user = new User(
                1,
                "vasilisa@gmail.com",
                "vasi lisa",
                "Vasilisa",
                LocalDate.of(1986, 10, 20));
        final ValidationException  exception = assertThrows(
                ValidationException.class,
                () -> controller.create(user)
        );
        assertEquals("Логин не может быть пустым и содержать пробелы.", exception.getMessage());
    }
    @Test
    void incorrectBirthdayExceptionTest() {
        user = new User(
                1,
                "vasilisa@gmail.com",
                "vasilisa",
                "Vasilisa",
                LocalDate.now().plusDays(1));
        final ValidationException exception = assertThrows(
                ValidationException.class,
                () -> controller.create(user)
        );
        assertEquals("Дата рождения не может быть в будущем.", exception.getMessage());
    }
    @Test
    void emptyNameShouldreplaceByLogenTest() {
        user = new User(
                1,
                "vasilisa@gmail.com",
                "vasilisa",
                "",
                LocalDate.of(1986, 10, 20));
        User user1 = controller.create(user);

        assertEquals(user1.getName(), user1.getLogin());
    }
}
