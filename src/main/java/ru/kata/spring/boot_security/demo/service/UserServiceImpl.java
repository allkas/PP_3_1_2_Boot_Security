package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    @Transactional
    public void saveUser(User user) {
        RoleRepository roleRepository = null;
        Collection<Role> roles = user.getRoles().stream()
                .map(role -> {
                    assert false;
                    return roleRepository.findByName(role.getName())
                            .orElseThrow(() -> new IllegalArgumentException("Invalid role: " + role.getName()));
                })
                .collect(Collectors.toList());
        user.setRoles(roles);
        userRepository.save(user);
    }
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}