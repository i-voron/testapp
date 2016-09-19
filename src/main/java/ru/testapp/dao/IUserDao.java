package ru.testapp.dao;

import ru.testapp.entity.User;

public interface IUserDao {
    User getUser(String login);
}
