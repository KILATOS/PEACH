package org.peach.app.models;

import org.peach.app.config.SpringConfig;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    @NotEmpty (message = "name shouldn`t be empty")
    private String name;
    @Column(name = "year")
    @Positive(message = "year should be greater than 0")
    @Max(value = SpringConfig.maxYearInBook, message = "year should be less than " + SpringConfig.maxYearInBook)
    private int year;
    @Column(name = "author")
    @NotEmpty (message = "author shouldn`t be empty")
    @Pattern(regexp = "[a-zA-Z `.\\-]+" , message = "name should contains only letters")
    private String author;


    @Column(name = "istaken")
    private boolean istaken;

    @ManyToMany
    @JoinTable(name = "users_books",
    joinColumns = @JoinColumn(name = "book_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    public Book(String name, int year, String author) {
        this.name = name;
        this.year = year;
        this.author = author;
    }

    public Book() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean isIstaken() {
        return istaken;
    }

    public void setIstaken(boolean istaken) {
        this.istaken = istaken;
    }
}
