package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addFriend(Integer userId, Integer friendId) {
        if (userId < 0 || friendId < 0) {
            log.error("Передан отрицатальный id");
            throw new NotFoundException("Передан отрицатальный id");
        }
        User user = userStorage.getUserById(userId);
        user.addFriend(friendId);
        User friend = userStorage.getUserById(friendId);
        friend.addFriend(userId);
        return user;
    }

    public User deleteFriend(Integer userId, Integer friendId) {
        if (userId < 0 || friendId < 0) {
            log.error("Передан отрицатальный id");
            throw new NotFoundException("Передан отрицатальный id");
        }
        User user = userStorage.getUserById(userId);
        user.getFriends().remove(friendId);
        return user;
    }

    public List<User> findJoinFriends(Integer userId, Integer friendId) {
        if (userId < 0 || friendId < 0) {
            log.error("Передан отрицатальный id");
            throw new NotFoundException("Передан отрицатальный id");
        }
        User user = userStorage.getUserById(userId);
        Set<Integer> listFriendUser = user.getFriends();
        User friend = userStorage.getUserById(friendId);
        Set<Integer> listFriendToFriend = friend.getFriends();
        Set<Integer> resultList = new HashSet<>(listFriendToFriend);
        resultList.retainAll(listFriendUser);
        return resultList.stream()
                .map(e -> userStorage.getUserById(e))
                .collect(Collectors.toList());
    }

    public List<User> findFriends(Integer userId) {
        if (userId < 0) {
            log.error("Передан отрицатальный id");
            throw new NotFoundException("Передан отрицатальный id");
        }
        List<User> friends = new ArrayList<>();
        User user = userStorage.getUserById(userId);
        Set<Integer> listFriendUser = user.getFriends();
        if (user != null) {
            friends =  listFriendUser.stream()
                    .map(e -> userStorage.getUserById(e))
                    .collect(Collectors.toList());
        }

        return friends;
    }
}
