package ru.yandex.practicum.filmorate.storage.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@Slf4j
@Component("UserDbStorage")
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User getUserById(int id) {
        String sqlUser = "select * from USERS where USERID = ?";
        User user;
        try {
            user = jdbcTemplate.queryForObject(sqlUser, (rs, rowNum) -> makeUser(rs), id);
            return user;
        }
        catch (NotFoundException e) {
            log.error("Пользователь c id {} не найден.", id);
            throw new NotFoundException("Пользователь c id" + id + "не найден");
        }

    }

    @Override
    public Collection<User> findAll() {
        String sqlAllUsers = "select * from USERS";
        return jdbcTemplate.query(sqlAllUsers, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public User create(User user) {
        String sqlQuery = "insert into USERS " +
                "(EMAIL, LOGIN, NAME, BIRTHDAY) " +
                "values (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setDate(4, Date.valueOf(user.getBirthday()));

            return preparedStatement;
        }, keyHolder);

        int id = Objects.requireNonNull(keyHolder.getKey()).intValue();

        if (user.getFriends() != null) {
            for (Integer friendId : user.getFriends()) {
                addFriend(user.getId(), friendId);
            }
        }
        return getUserById(id);
    }

    @Override
    public User update(User user) {
        String sqlUser = "update USERS set " +
                "EMAIL = ?, LOGIN = ?, NAME = ?, BIRTHDAY = ? " +
                "where USERID = ?";
        int countUpdateRow = jdbcTemplate.update(sqlUser,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        if (countUpdateRow != 0) {
            return user;
        }
        log.error("ID не найден");
        throw new NotFoundException("ID не найден");
    }

    private User makeUser(ResultSet resultSet) throws SQLException {
        int userId = resultSet.getInt("UserID");
        return new User(
                userId,
                resultSet.getString("Email"),
                resultSet.getString("Login"),
                resultSet.getString("Name"),
                Objects.requireNonNull(resultSet.getDate("Birthday")).toLocalDate(),
                new HashSet<>(getUserFriends(userId)));
    }

    public List<Integer> getUserFriends(Integer userId) {
        String sqlGetFriends = "SELECT * FROM users WHERE userid IN (SELECT friendid FROM FRIENDSHIP WHERE userid =?)";
        List<Integer> friends = jdbcTemplate.queryForList(sqlGetFriends, Integer.class, userId);

        if (friends.isEmpty()) {
            return Collections.emptyList();
        }
        return friends;
    }

    @Override
    public boolean addFriend(int userId, int friendId) {
        String sqlSetFriend = "insert into FRIENDSHIP (USERID, FRIENDID, STATUS) " +
                "VALUES (?,?,?)";
        jdbcTemplate.update(sqlSetFriend, userId, friendId);
        return true;
    }

    @Override
    public boolean deleteFriend(int userId, int friendId) {
        String sqlDeleteFriend = "delete from FRIENDSHIP where USERID = ? and FRIENDID = ?";
        jdbcTemplate.update(sqlDeleteFriend, userId, friendId);
        return true;
    }

    public List<Integer> findCommonFriends(Integer userId, Integer friendId) {
        String sql = "SELECT u.userid, email, login, name, birthday\n" +
                "FROM FRIENDSHIP as f1\n" +
                "JOIN FRIENDSHIP as f2 ON  f2.FRIENDID = f1.FRIENDID AND f2.USERID = ?\n" +
                "JOIN USERS U on U.USERID = f1.FRIENDID\n" +
                "WHERE f1.USERID = ?";

        List<Integer> resultList = jdbcTemplate.queryForList(sql, Integer.class,  userId, friendId);

        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }
        return resultList;
    }
}
