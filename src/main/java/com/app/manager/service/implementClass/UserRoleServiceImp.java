package com.app.manager.service.implementClass;

import com.app.manager.entity.UserRole;
import com.app.manager.model.returnResult.DatabaseQueryResult;
import com.app.manager.repository.UserRoleRepository;
import com.app.manager.service.interfaceClass.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImp implements UserRoleService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    UserRoleRepository userroleRepository;

    @Override
    public List<UserRole> getAll() {
        return (List<UserRole>) userroleRepository.findAll();
    }

    @Override
    public DatabaseQueryResult add(UserRole userRole) {
        try {
            userroleRepository.save(userRole);
            return new DatabaseQueryResult(true,
                    "add user role success", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new DatabaseQueryResult(false,
                    "add user role failed, "
                            + e.getMessage() ,
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
