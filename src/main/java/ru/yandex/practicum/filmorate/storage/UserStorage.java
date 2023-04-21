package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    User updateUser(User user);

    User getById(int id);

    List<User> getUsersList();

    void addFriend(int userId, int friendId);

    void deleteFriend(int userId, int friendId);
}