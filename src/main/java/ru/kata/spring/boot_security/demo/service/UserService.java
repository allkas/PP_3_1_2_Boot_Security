package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    public User findById(Long id);
    public List<User> findAll();
    public void saveUser(User user);
    public void deleteById(Long id);
    public User findByEmail(String email);
}