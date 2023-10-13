package org.peach.app.controllers;

import org.peach.app.models.Book;
import org.peach.app.models.User;
import org.peach.app.dao.BooksDAO;
import org.peach.app.dao.UsersDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.peach.app.util.BookValidator;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksDAO booksDAO;
    private final UsersDAO usersDAO;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BooksDAO booksDAO, UsersDAO usersDAO, BookValidator bookValidator) {
        this.booksDAO = booksDAO;
        this.usersDAO = usersDAO;
        this.bookValidator = bookValidator;

    }

    @GetMapping()
    public String booksIndex(Model model){
        model.addAttribute("allBooks", booksDAO.getAllBooks());
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
        booksDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String getOneBook(@PathVariable("id") long id, Model model){
        Book curBook = booksDAO.findOne(id);
        model.addAttribute("curBook",curBook);
        if (curBook.isIstaken()) {
            Optional<User> curUser = booksDAO.ownerCheck(id);
            curUser.ifPresent(user -> model.addAttribute("curUser", user));
        }
        return "books/book";
    }


    @GetMapping("/{id}/edit")
    public String requestToEditBook(@PathVariable("id") long id,
                                    Model model){
        model.addAttribute("curBook", booksDAO.findOne(id));
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
        booksDAO.updateBook(book,id);
        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@ModelAttribute("curBook")Book book){
        booksDAO.delete(book);
        return "redirect:/books";
    }
    @GetMapping("/appoint/{id}")
    public String showAppointPage(Model model,
                                  @ModelAttribute("chosenUser") User user,
                                  @PathVariable("id") long id
                                  ){
        model.addAttribute("curBook", booksDAO.findOne(id));
        model.addAttribute("users", usersDAO.index());
        return "books/choose";
    }


    @PostMapping("/appoint/{id}")
    public String appointBookToUser(Model model,
                                    @ModelAttribute("chosenUser") User user,
                                    @PathVariable("id") long id){
        booksDAO.appointBook(user,id);
        return "redirect:/books";
    }
    @PostMapping("/release/{id}")
    public String releaseBook(Model model,
                              @PathVariable("id") long id){
        booksDAO.releaseBook(id);
        return "redirect:/books/{id}";
    }




}
