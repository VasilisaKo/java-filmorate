package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserStorage userStorage;
    private final UserService userService;

    @Autowired
    public UserController(UserStorage userStorage,
                          UserService userService) {
        this.userStorage = userStorage;
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> findAll() {
        return userStorage.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer userId) {
        if (userId != null && userId > 0) {
            return userStorage.getUserById(userId);
        }
        log.debug("Некорректный UserId");
        throw new ValidationException("userId");
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userStorage.create(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return userStorage.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable("id") Integer userId,
                          @PathVariable("friendId") Integer friendId) {
        return userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable("id") Integer userId,
                             @PathVariable("friendId") Integer friendId) {
        return userService.deleteFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<User> getFriends(@PathVariable("id") Integer userId) {
        return userService.findFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<User> getJoinFriends(@PathVariable("id") Integer userId,
                                          @PathVariable("otherId") Integer friendId) {
        return userService.findJoinFriends(userId, friendId);
    }
}
