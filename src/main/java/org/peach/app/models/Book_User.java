package org.peach.app.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users_books")
public class Book_User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_id")
    private long userId;
    @Column(name = "book_id")
    private long bookId;
    @Column(name = "time")
    private LocalDateTime time;

    public Book_User() {
    }

    public Book_User(long userId, long book_id, LocalDateTime time) {
        this.userId = userId;
        this.bookId = book_id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getBook_id() {
        return bookId;
    }

    public void setBook_id(long book_id) {
        this.bookId = book_id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
