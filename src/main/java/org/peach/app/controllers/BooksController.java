package org.peach.app.controllers;

import org.peach.app.models.Book;
import org.peach.app.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public String getOneBook(@PathVariable("id") long id, Model model){
        model.addAttribute("curBook",booksRepository.findOne(id));
        return "books/book";
    }


    @GetMapping("/{id}/edit")
    public String requestToEditBook(@PathVariable("id") long id,
                                    Model model){
        model.addAttribute("curBook", booksRepository.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String editingBook(@ModelAttribute("curBook") Book book,
                              @PathVariable("id") long id){
        booksRepository.updateBook(book,id);
        return "redirect:/books/{id}";
    }



}
