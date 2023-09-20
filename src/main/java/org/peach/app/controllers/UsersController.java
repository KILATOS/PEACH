package org.peach.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UsersController {
    @GetMapping("/")
    public String index(Model model){

        return null;
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") long id, Model model){
        return null;
    }



}
