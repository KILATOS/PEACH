package org.peach.app.services;

import org.peach.app.exceptions.BookNotFoundException;
import org.peach.app.exceptions.CannotDeleteBookException;
import org.peach.app.models.Book;
import org.peach.app.repositories.BooksRepository;
import org.peach.app.util.advanced_search.BookSearcher;
import org.peach.app.util.advanced_search.FilterBookSearcher;
import org.peach.app.util.search_config.BookSearchConfig;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;
    private static int lastPageIndex = -1;

    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> getAllBooks(){
        return booksRepository.findAll();
    }
    public Book findOne(long id) throws BookNotFoundException {
        Optional<Book> curBook = booksRepository.findById(id);
        if (curBook.isPresent()){
            return curBook.get();
        } else {
            throw new BookNotFoundException(String.format("Book with id %d isn`t found", id));
        }
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(Book book, long id){
        book.setId(id);
        booksRepository.save(book);
    }
    @Transactional
    public void delete(Book book) throws CannotDeleteBookException{
        Book curBook = booksRepository.getOne(book.getId());
        if (!curBook.isIstaken()) {
            booksRepository.delete(curBook);
        } else {
            throw new CannotDeleteBookException(
                    String.format(
                    "Book with id %d cannot be deleted `cause one has an owner", curBook.getId())
            );
        }
    }
    public List<Book> getBooks(BookSearchConfig searchConfig){
        List<Book> list = booksRepository.findAll(PageRequest.of(
                searchConfig.getNumberOfPage(),
                searchConfig.getBooksPerPage())).getContent();
        BookSearcher bookSearcher = new FilterBookSearcher(searchConfig);
        /*if (list.isEmpty()){
            lastPageIndex = page - 1;
            return booksRepository.findAll(PageRequest.of(page - 1,booksPerPage)).getContent();
        }*/
        return bookSearcher.bookSearcherManager(list);
    }

    public int getLastPageIndex() {
        return lastPageIndex;
    }
}
