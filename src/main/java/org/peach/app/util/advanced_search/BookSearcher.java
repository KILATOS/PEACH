package org.peach.app.util.advanced_search;

import org.peach.app.models.Book;
import org.peach.app.util.search_config.BookSearchConfig;

import java.util.List;

public abstract class BookSearcher {
    protected  BookSearchConfig bookSearchConfig;
    protected BookSearcher nextBookSearcher;

    public BookSearcher(BookSearchConfig bookSearchConfig) {
        this.bookSearchConfig = bookSearchConfig;
    }

    public void setBookSearcher(BookSearcher bookSearcher) {
        this.nextBookSearcher = bookSearcher;
    }
    protected abstract List<Book> getBooks(List<Book> list);
    public abstract List<Book>  bookSearcherManager(List<Book> list);
}
