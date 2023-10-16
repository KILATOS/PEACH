package org.peach.app.services;

import org.peach.app.models.Book;
import org.peach.app.models.Book_User;
import org.peach.app.models.User;
import org.peach.app.repositories.BooksRepository;
import org.peach.app.repositories.BooksUsersRepository;
import org.peach.app.repositories.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@Transactional(readOnly = true)
public class BooksUsersService {
    private final BooksUsersRepository booksUsersRepository;
    private final UsersRepository usersRepository;
    private final BooksRepository booksRepository;

    public BooksUsersService(BooksUsersRepository booksUsersRepository, UsersRepository usersRepository, BooksRepository booksRepository) {
        this.booksUsersRepository = booksUsersRepository;
        this.usersRepository = usersRepository;
        this.booksRepository = booksRepository;
    }

    public Book_User findFirstByBookIdOrderByTimeDesc(long bookId) {
        return booksUsersRepository.findFirstByBookIdOrderByTimeDesc(bookId);
    }

    @Transactional
    public void appointBook(User user, long bookId) {
        Date curDate = new Date();
        booksUsersRepository.save(new Book_User(user.getId(), bookId, curDate));
        User curUser = usersRepository.getOne(user.getId());
        Book curBook = booksRepository.getOne(bookId);
        curUser.setHasBook(true);
        curBook.setIstaken(true);
    }

    @Transactional
    public void releaseBook(long id) {
        Book curBook = booksRepository.getOne(id);
        Book_User currentRelation = findFirstByBookIdOrderByTimeDesc(id);
        User owner = usersRepository.getOne(currentRelation.getUserId());
        owner.setHasBook(false);
        curBook.setIstaken(false);
    }


}
