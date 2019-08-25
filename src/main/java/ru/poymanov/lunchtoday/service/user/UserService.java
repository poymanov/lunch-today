package ru.poymanov.lunchtoday.service.user;

import ru.poymanov.lunchtoday.model.User;
import ru.poymanov.lunchtoday.to.UserTo;
import ru.poymanov.lunchtoday.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();

    void enable(int id, boolean enable);
}