package com.app.manager.service.implementClass;

import com.app.manager.entity.Role;
import com.app.manager.repository.RoleRepository;
import com.app.manager.service.interfaceClass.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImp implements RoleService {
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return (List<Role>) roleRepository.findAll();
    }

    @Override
    public Optional<Role> find(String id) {
        var role = roleRepository.findRoleById(id);
        if(role != null) return Optional.of(role);
        return Optional.empty();
    }
    @Override
    public Optional<Role> findBasicRole(String name) {
        var role = roleRepository.findFirstByName("ROLE_USER");
        if(role != null) return Optional.of(role);
        return Optional.empty();
    }
}
