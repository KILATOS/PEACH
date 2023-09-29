package org.peach.app.controllers;

import org.peach.app.models.User;
import org.peach.app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UsersRepository usersRepository;
    @Autowired
    public AdminController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping()
    public String getAdminPage( Model model , @ModelAttribute("curUser") User curUser){
        List<User> allUsers = usersRepository.index();
        model.addAttribute("allUsers",allUsers);

        return "admin/choose";
    }
    @PostMapping()
    public String selectingAdmin(@ModelAttribute("curUser") User curUser){
        usersRepository.updateIsAdmin(curUser);
        return "redirect:/users";
    }


}
