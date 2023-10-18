package org.peach.app.controllers;

import org.peach.app.exceptions.BookNotFoundException;
import org.peach.app.exceptions.CannotDeleteBookException;
import org.peach.app.models.Book;
import org.peach.app.models.Book_User;
import org.peach.app.models.User;

import org.peach.app.services.BookService;
import org.peach.app.services.BooksUsersService;
import org.peach.app.services.UserService;
import org.peach.app.util.search_config.constants.BookFilter;
import org.peach.app.util.search_config.BookSearchConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.peach.app.util.validators.BookValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final UserService userService;
    private final BookService bookService;
    private final BooksUsersService booksUsersService;

    private final BookValidator bookValidator;

    @Autowired
    public BooksController(UserService userService1, BookService bookService1, BooksUsersService booksUsersService, BookValidator bookValidator) {
        this.userService = userService1;
        this.bookService = bookService1;
        this.booksUsersService = booksUsersService;

        this.bookValidator = bookValidator;

    }

    @GetMapping()
    public String booksIndex(HttpServletRequest httpServletRequest,   // in work
                             Model model,
                             @ModelAttribute("searchConfig") BookSearchConfig searchConfig){
        int booksPerPage = 6;
        int numberOfPage = 0;
        try {
            numberOfPage = Integer.parseInt(httpServletRequest.getParameter("page"));
            if (numberOfPage<0){
                numberOfPage = 0;
            }
        } catch (NumberFormatException ignore) {}
        searchConfig.setBooksPerPage(booksPerPage);
        searchConfig.setNumberOfPage(numberOfPage);
        model.addAttribute("filter", searchConfig.getFilter());
        model.addAttribute("allBooks", bookService.getBooks(searchConfig));
        model.addAttribute("numberOfPage", numberOfPage);
        model.addAttribute("filters", BookFilter.values());
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
        bookService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String getOneBook(@PathVariable("id") long id, Model model){
        Book curBook = null;
        try {
            curBook = bookService.findOne(id);
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "errors/bookNotFound";
        }
        model.addAttribute("curBook",curBook);
        if (curBook.isIstaken()) {
            Book_User book_user = booksUsersService.findFirstByBookIdOrderByTimeDesc(id);
            User curUser = userService.findOne(book_user.getUserId());
            model.addAttribute("curUser", curUser);
        }
        return "books/book";
    }


    @GetMapping("/{id}/edit")
    public String requestToEditBook(@PathVariable("id") long id,
                                    Model model){
        try {
            model.addAttribute("curBook", bookService.findOne(id));
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "errors/bookNotFound";
        }
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
        bookService.update(book,id);

        return "redirect:/books/{id}";
    }
    @DeleteMapping("/{id}")
    public String deleteBook(@ModelAttribute("curBook")Book book){
        try {
            bookService.delete(book);
        } catch (CannotDeleteBookException e) {
            e.printStackTrace();
            return "errors/cannotDeleteBook";
        }
        return "redirect:/books";
    }
    @GetMapping("/appoint/{id}")
    public String showAppointPage(Model model,
                                  @ModelAttribute("chosenUser") User user,
                                  @PathVariable("id") long id
                                  ){
        try {
            model.addAttribute("curBook", bookService.findOne(id));
        } catch (BookNotFoundException e) {
            e.printStackTrace();
            return "errors/bookNotFound";
        }
        model.addAttribute("users", userService.findAll());
        return "books/choose";
    }


    @PostMapping("/appoint/{id}")
    public String appointBookToUser(Model model,
                                    @ModelAttribute("chosenUser") User user,
                                    @PathVariable("id") long id){

        //booksDAO.appointBook(user,id);
        booksUsersService.appointBook(user,id);
        return "redirect:/books";
    }
    @PostMapping("/release/{id}")
    public String releaseBook(Model model,
                              @PathVariable("id") long id){
        //booksDAO.releaseBook(id);
        booksUsersService.releaseBook(id);
        return "redirect:/books/{id}";
    }




}
