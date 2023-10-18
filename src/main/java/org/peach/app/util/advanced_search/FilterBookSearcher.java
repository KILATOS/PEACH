package org.peach.app.util.advanced_search;

import org.peach.app.models.Book;
import org.peach.app.util.search_config.BookSearchConfig;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilterBookSearcher extends BookSearcher{
    public FilterBookSearcher(BookSearchConfig bookSearchConfig) {
        super(bookSearchConfig);
        this.nextBookSearcher = new FinderBookSearcher(bookSearchConfig);
    }

    @Override
    protected List<Book> getBooks(List<Book> list) {

        if (bookSearchConfig.getFilter() == null){
            return list;
        }
        switch (bookSearchConfig.getFilter()){
            case YEAR:
                return list.stream().sorted(Comparator.comparingInt(Book::getYear)).collect(Collectors.toList());
            default:
                return list;
        }
    }

    @Override
    public List<Book> bookSearcherManager(List<Book> list) {
        List<Book> curArray = getBooks(list);
        if (bookSearchConfig.getStringToFind()!=null){
            return this.nextBookSearcher.bookSearcherManager(curArray);
        } else {
            return curArray;
        }
    }
}
