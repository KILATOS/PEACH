package org.peach.app.controllers;

import org.peach.app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/batchTest")
public class BatchController {
    private final UsersRepository usersRepository;
    @Autowired
    public BatchController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @GetMapping("")
    public String index(){
        return "batch/index";
    }

    @GetMapping("/without")
    public String testWithoutBatch(){
        usersRepository.insertMultiple();
        return "redirect:/users";

    }

    @GetMapping("/with")
    public String testWithBatch(){
        usersRepository.insertBatch();
        return "redirect:/users";

    }
    @GetMapping("/deleting")
    public String batchDeleting(){
        usersRepository.deleteBatch();
        return "redirect:/users";
    }
}
