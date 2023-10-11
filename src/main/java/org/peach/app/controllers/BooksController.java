package org.peach.app.controllers;

import org.peach.app.models.Book;
import org.peach.app.models.User;
import org.peach.app.repositories.BooksRepository;
import org.peach.app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.peach.app.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksRepository booksRepository;
    private final UsersRepository usersRepository;
    private final BookValidator bookValidator;
    @Autowired
    public BooksController(BooksRepository booksRepository, UsersRepository usersRepository, BookValidator bookValidator) {
        this.booksRepository = booksRepository;
        this.usersRepository = usersRepository;
        this.bookValidator = bookValidator;
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
        System.out.println(book.getYear());
        bookValidator.validate(book,bindingResult);
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
    public String editingBook(@ModelAttribute("curBook") @Valid Book book,
                              BindingResult bindingResult,
                              @PathVariable("id") long id){
        bookValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()){
            return "books/edit";
        }
        booksRepository.updateBook(book,id);
        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@ModelAttribute("curBook")Book book){
        booksRepository.delete(book);
        return "redirect:/books";
    }
    @GetMapping("/appoint/{id}")
    public String showAppointPage(Model model,
                                  @ModelAttribute("chosenUser") User user,
                                  @PathVariable("id") long id
                                  ){
        model.addAttribute("curBook", booksRepository.findOne(id));
        model.addAttribute("users", usersRepository.index());
        return "books/choose";
    }


    @PostMapping("/appoint/{id}")
    public String appointBookToUser(Model model,
                                    @ModelAttribute("chosenUser") User user,
                                    @PathVariable("id") long id){
        booksRepository.appointBook(user,id);
        return "redirect:/books";
    }




}
