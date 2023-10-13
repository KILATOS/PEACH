package org.peach.app.repositories;

import org.peach.app.models.Book_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksUsersRepository extends JpaRepository<Book_User, Integer> {
    public Book_User findFirstByBookIdOrderByTimeDesc(long bookId);
}
