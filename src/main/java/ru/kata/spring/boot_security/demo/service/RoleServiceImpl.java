package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Arrays;
import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    @Override
    public List<Roles> findAllRoles() {
        return Arrays.asList(Roles.values());
    }

}
