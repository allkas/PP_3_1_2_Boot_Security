package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.Roles;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    public String findAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/user-create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", Roles.values());
        return "user-create";
    }

//    @PostMapping("/user-create")
//    public String createUser(@ModelAttribute("user") User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "user-create";
//        }
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user, @RequestParam("roles") List<Roles> selectedRoles) {
        user.setRoles(selectedRoles.stream()
                .map(Role::new) // Преобразуем RoleEnum в сущность Role
                .collect(Collectors.toList()));
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/user-delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @GetMapping("/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", Roles.values());
        return "user-update";
    }
    @PostMapping("/user-update")
    public String updateUser(@RequestParam("id") Long id,
                             @ModelAttribute("user") User user,
                             @RequestParam("roles") List<Roles> selectedRoles) {
        // Устанавливаем выбранные роли
        user.setRoles(selectedRoles.stream()
                .map(Role::new)
                .collect(Collectors.toList()));

        // Сохраняем обновлённого пользователя
        userService.saveUser(user);
        return "redirect:/users";
    }
//    @PostMapping("/user-update")
//    public String updateUser(@RequestParam("id") Long id, @ModelAttribute("user") User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "user-update";
//        }
//        user.setId(id);
//        userService.saveUser(user);
//        return "redirect:/users";
//    }
}
