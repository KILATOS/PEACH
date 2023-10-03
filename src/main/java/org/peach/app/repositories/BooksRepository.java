package org.peach.app.repositories;

import org.peach.app.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Component
public class BooksRepository {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BooksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks(){
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }
    public void save(Book book){
        try {
            jdbcTemplate.update("INSERT INTO books (name, year, author) VALUES (?,?,?)",
                    book.getName(),
                    book.getYear(),
                    book.getAuthor());
            System.out.println(new Date().toString() + "success");
        } catch (DataAccessException e) {
            e.printStackTrace();
            System.out.println( new Date().toString() + "fail");
        }
    }
    public Book findOne(long id){
        try {
            return jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id).
                    stream().findAny().orElseThrow((Supplier<Throwable>) NullPointerException::new);

        } catch (Throwable e) {
            System.out.println("Error in finding one book in DB");
            e.printStackTrace();
            return new Book();
        }
    }



}
