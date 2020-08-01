package com.app.manager.service.interfaceClass;

import com.app.manager.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public List<Role> getAll();
    Optional<Role> find(String id);
    Optional<Role> findBasicRole(String name);
}
