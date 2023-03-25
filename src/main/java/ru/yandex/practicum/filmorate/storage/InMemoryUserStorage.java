package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.IncorrectParameterException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage{
    private final Map<Integer, User> users = new HashMap<>();
    private int generateUserId = 1;

    @Override
    public User create(User user) {
        if(user.getEmail() == null || !user.getEmail().contains("@")) {
            log.error("Электронная почта не может быть пустой и должна содержать символ @.");
            throw new ValidationException("Электронная почта не может быть пустой и должна содержать символ @.");
        }
        if (user.getLogin() == null || user.getLogin().contains(" ")){
            log.error("Логин не может быть пустым и содержать пробелы.");
            throw new ValidationException("Логин не может быть пустым и содержать пробелы.");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Дата рождения не может быть в будущем.");
            throw new ValidationException("Дата рождения не может быть в будущем.");
        }
        user.setId(generateUserId++);
        users.put(user.getId(), user);
        log.debug("Создан новый пользователь: {}", user);
        return user;
    }

    @Override
    public User update(User user) {

        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            log.debug("Пользователь обновлен: {}", user);
            return user;
        } else {
            log.error("Пользователь с id {} не найден", user.getId());
            throw new IncorrectParameterException("Пользователь с id " + user.getId() + "не найден");
        }
    }

    @Override
    public Collection<User> findAll() {
        log.debug("Текущее количество пользователей: {}", users.size());
        return users.values();
    }

    @Override
    public User getUserById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new NotFoundException("Пользователь c id" + id + "не найден");
    }
}
