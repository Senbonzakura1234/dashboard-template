package com.app.manager.service.interfaceClass;

import com.app.manager.entity.UserRole;
import com.app.manager.model.returnResult.DatabaseQueryResult;

import java.util.List;

public interface UserRoleService {
    public List<UserRole> getAll();

    DatabaseQueryResult add(UserRole userRole);
}
