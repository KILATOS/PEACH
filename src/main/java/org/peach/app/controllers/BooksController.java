package org.peach.app.controllers;

import org.peach.app.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksRepository booksRepository;
    @Autowired
    public BooksController(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping()
    public String booksIndex(Model model){
        model.addAttribute("allBooks", booksRepository.getAllBooks());
        return "books/index";
    }
}
