package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Roles;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    public List<Roles> findAllRoles();
}
