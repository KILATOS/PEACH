package org.peach.app.controllers;

import org.peach.app.models.User;
import org.peach.app.repositories.UsersRepository;
import org.peach.app.util.UserValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UsersRepository usersRepository;
    private final UserValidator userValidator;

    public UsersController(UsersRepository usersRepository, UserValidator userValidator) {
        this.usersRepository = usersRepository;
        this.userValidator = userValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("users",usersRepository.index());

        return "users/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        model.addAttribute("id",id);
        model.addAttribute("user",usersRepository.findOneById(id));
        return "users/user";
    }


    @GetMapping("/new")
    public String requestToAddNewUser(@ModelAttribute("newUser") User user){
        return "users/new";
    }
    @PostMapping()
    public String createUser(   @ModelAttribute("newUser")
                                 @Valid User user,
                                BindingResult bindingResult){
        //userValidator.validate(user,bindingResult);
        if (bindingResult.hasErrors()){
            return "users/new";
        }
        usersRepository.save(user);
        return "redirect:/users";
    }
    @GetMapping("/{id}/edit")
    public String requestToEditUser(Model model, @PathVariable("id") long id){
        model.addAttribute("curUser", usersRepository.findOneById(id));
        return "users/edit";
    }
    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("curUser") @Valid User user,
                           BindingResult bindingResult,
                           @PathVariable("id") long id){
        userValidator.validate(user,bindingResult);
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
