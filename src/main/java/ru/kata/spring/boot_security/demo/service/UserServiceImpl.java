package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void saveUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }


    @Transactional
    public void updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (updatedUser.getPassword() == null || updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(existingUser.getPassword());
        } else if (!updatedUser.getPassword().equals(existingUser.getPassword())) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        if (updatedUser.getRoles() != null) {
            existingUser.getRoles().clear(); // Удаляем старые роли
            existingUser.getRoles().addAll(updatedUser.getRoles()); // Добавляем новые роли
        }
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());

        userRepository.save(existingUser);
    }



    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}