package org.peach.app.repositories;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.peach.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UsersRepository {

   private final SessionFactory sessionFactory;
    @Autowired
    public UsersRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public User findOneById(long id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class,id);
    }
    @Transactional
    public Optional<User> findOneByName(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT u from User u where u.name = :name", User.class).setParameter("name",name).
                getResultList().
                stream().
                findAny();
    }
    @Transactional
    public void updateOne(long id, User updatedUser){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(updatedUser); // here can be troubles
    }

    @Transactional
    public List<User> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select u from User u", User.class).getResultList();
    }
    @Transactional
    public void save(User user){
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }
    @Transactional
    public void delete(long id){
        Session session = sessionFactory.getCurrentSession();
        User userTODelete = session.get(User.class, id);
        session.delete(userTODelete);
    }

}
