package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    @GetMapping
//    public String adminHome(@AuthenticationPrincipal UserDetails currentUser, ModelMap model) {
//        //List<User> users = userService.findAll();
//
//        model.addAttribute("user", currentUser);
//        model.addAttribute("users", userService.findAll());
//        model.addAttribute("allRoles", roleService.findAllRoles());
//        model.addAttribute("newUser", new User()); // Для формы создания пользователя
//
//        return "admin";
//    }
    @GetMapping
    public String adminHome(ModelMap model, Principal principal) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
        } else {
            return "redirect:/login";
        }
        model.addAttribute("allRoles", roleService.findAllRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("currentUser", user);


        return "admin";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("newUser") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("allRoles", roleService.findAllRoles());
            return "admin";
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/user-update")
    public String updateUser(@ModelAttribute("user") User updatedUser, BindingResult result) {
        if (result.hasErrors()) {
            return "admin";
        }
        userService.updateUser(updatedUser.getId(), updatedUser);
        return "redirect:/admin";
    }

    @PostMapping("/user-delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}
