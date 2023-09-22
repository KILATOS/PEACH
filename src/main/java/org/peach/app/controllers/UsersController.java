package org.peach.app.controllers;

import org.peach.app.models.User;
import org.peach.app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UsersRepository usersRepository;

    public UsersController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users",usersRepository.index());

        return "users/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("id",id);
        model.addAttribute("user",usersRepository.findOne(id));
        return "users/user";
    }


    @GetMapping("/new")
    public String requestToAddNewUser(@ModelAttribute("newUser") User user){
        return "users/new";
    }
    @PostMapping()
    public String createUser(@ModelAttribute("newUser") @Valid User user,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "users/new";
        }
        usersRepository.save(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String requestToEditUser(Model model, @PathVariable("id") long id){
        model.addAttribute("curUser", usersRepository.findOne(id));
        return "users/edit";
    }
    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("curUser") @Valid User user,
                           BindingResult bindingResult,
                           @PathVariable("id") long id){
        if (bindingResult.hasErrors()){
            return "users/edit";
        }
        usersRepository.updateOne(id,user);
        return "redirect:/users";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id){
        usersRepository.delete(id);
        return "redirect:/users";
    }





}
