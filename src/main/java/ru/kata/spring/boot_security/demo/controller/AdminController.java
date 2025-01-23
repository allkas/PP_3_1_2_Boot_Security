//package ru.kata.spring.boot_security.demo.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import ru.kata.spring.boot_security.demo.model.Role;
//import ru.kata.spring.boot_security.demo.model.User;
//import ru.kata.spring.boot_security.demo.service.RoleService;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    UserService userService;
//    RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    public String show(Model model) {
//        List<User> users = userService.getAll();
//        model.addAttribute("allUsers", users);
//        return "admin";
//    }
//
//
//    @GetMapping("/new")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String addUserForm(Model model) {
//        model.addAttribute("user", new User());
//        List<Role> roles = roleService.getAllRoles();
//        model.addAttribute("roles", roles);
//        return "user-add";
//    }
//
//    @PostMapping("/add")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String saveUser(
//            @ModelAttribute("user") User user,
//            @RequestParam("roleIds") List<Long> roleIds
//    ) {
//        List<Role> roles = new ArrayList<>(roleService.getRolesByIds(roleIds));
//        userService.saveUser(user, roles);
//        return "redirect:/admin";
//    }
//
//
//    @GetMapping("/update")
//    public String showUpdateForm(@RequestParam Long id, Model model) {
//        model.addAttribute("user", userService.findById(id));
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "user-update";
//    }
//
//    @PostMapping("/saveUpdate")
//    public String update(@ModelAttribute("user") User user,
//                         @RequestParam List<Long> roleIds,
//                         Model model) {
//        List<Role> roles = roleService.getRolesByIds(roleIds);
//        userService.update(user, roles);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/delete")
//    public String delete ( @RequestParam Long id){
//        userService.delete(id);
//        return "redirect:/admin";
//    }
//}