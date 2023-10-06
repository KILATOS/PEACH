package org.peach.app.repositories;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.peach.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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



}
