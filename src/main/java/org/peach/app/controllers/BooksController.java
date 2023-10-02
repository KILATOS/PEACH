package org.peach.app.controllers;

import org.peach.app.models.Book;
import org.peach.app.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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
    @GetMapping("/new")
    public String requestToAddNewBook(@ModelAttribute("bookToAdd")Book book){
        return "books/new";
    }

    @PostMapping()
    public String addNewBook(@ModelAttribute("bookToAdd") @Valid Book book,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "books/new";
        }
        booksRepository.save(book);
        return "redirect:/books";
    }

}
