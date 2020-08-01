package com.app.manager.service.interfaceClass;

import com.app.manager.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();
    boolean add(User user);
    Optional<User> find(String id);
}
