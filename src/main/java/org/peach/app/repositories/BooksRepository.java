package org.peach.app.repositories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.mapping.Collection;
import org.peach.app.models.Book;
import org.peach.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;

@Component
public class BooksRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public BooksRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Book> getAllBooks(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }
    @Transactional
    public void save(Book book){
        Session session =  sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public Book findOne(long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class, id);

    }
    @Transactional
    public boolean updateBook(Book book, long id){
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(book); // here could be troubles
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }

    }
    @Transactional
    public void delete(Book book){
        Session session = sessionFactory.getCurrentSession();
        session.delete(book);
    }
    @Transactional
    public void appointBook(User user, long id){
        Session session = sessionFactory.getCurrentSession();
        session.createSQLQuery("insert into users_books (user_id, book_id, time) VALUES (:userId,:bookId,:time)")
                .setParameter("userId",user.getId())
                .setParameter("bookId", id)
                .setParameter("time", LocalDateTime.now()).executeUpdate();
        Book curBook = session.get(Book.class,id);
        User curUser = session.get(User.class, user.getId());
        curUser.setHasBook(true);
        curBook.setIstaken(true);
    }
    @Transactional
    public void releaseBook(long id){
        Session session = sessionFactory.getCurrentSession();
        Book curBook = session.get(Book.class, id);
        curBook.setIstaken(false);
        try {
            List users = session.
                    createSQLQuery("SELECT ub.user_id FROM users_books as ub where ub.book_id = :bookID order by ub.time desc ").
                    setParameter("bookID",curBook.getId()).
                    getResultList();
            BigInteger userId = (BigInteger) users.get(0);
            User curUser = session.get(User.class, userId.longValue());
            curUser.setHasBook(false);
        } catch (NullPointerException e) {
            System.out.println();
            System.out.printf("//////User doesn`t found, book %d released//////", curBook.getId());
            System.out.println();
        }
    }




}
