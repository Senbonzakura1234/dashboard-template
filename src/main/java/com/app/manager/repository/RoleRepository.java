package com.app.manager.repository;

import com.app.manager.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, String> {
    Role findRoleById (String id);
    Role findFirstByName(String name);
}
