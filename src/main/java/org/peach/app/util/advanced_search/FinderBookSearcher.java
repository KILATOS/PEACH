package org.peach.app.util.advanced_search;

import org.peach.app.models.Book;
import org.peach.app.util.search_config.BookSearchConfig;

import java.util.List;

public class FinderBookSearcher extends BookSearcher{
    public FinderBookSearcher(BookSearchConfig bookSearchConfig) {
        super(bookSearchConfig);
    }

    @Override
    protected List<Book> getBooks(List<Book> list) {
        return list;
    }

    @Override
    public List<Book>  bookSearcherManager(List<Book> list) {
        List<Book> curArray = getBooks(list);
        return curArray;

    }
}
