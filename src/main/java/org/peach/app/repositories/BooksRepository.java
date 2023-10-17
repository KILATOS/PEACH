package org.peach.app.repositories;

import org.peach.app.models.Book;
import org.peach.app.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Long> {
    @Override
    Page<Book> findAll(Pageable pageable);
}
