package com.tsimo.mylibrary.book;

import com.tsimo.mylibrary.entities.Book;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.commons.collections.ComparatorUtils;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

/**
 *
 * @author Tsimo
 */
public class LazyBookDataModel extends LazyDataModel<Book> {

    private final List<Book> datasource;

    public LazyBookDataModel(List<Book> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Book getRowData(String rowKey) {
        for (Book book : datasource) {
            if (book.getId() == Integer.parseInt(rowKey)) {
                return book;
            }
        }
        return null;
    }

    @Override
    public String getRowKey(Book book) {
        return String.valueOf(book.getId());
    }

    @Override
    public List<Book> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        List<Book> books = new ArrayList<>();

        for (Book book : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String fieldValue;
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty).getFilterValue();

                        if (filterProperty.equalsIgnoreCase("availability.availability")) {
                            fieldValue = book.getAvailability().getAvailability().toString();
                            match = fieldValue.equals(filterValue.toString());
                            if(!match) break;
                        } else {
                            if (filterProperty.equalsIgnoreCase("bookAuthorListToString")) {
                                fieldValue = book.getBookAuthorListToString();
                            } else {
                                Field privateMember = book.getClass().getDeclaredField(filterProperty);
                                privateMember.setAccessible(true);
                                fieldValue = String.valueOf(privateMember.get(book));
                            }
                            if (filterValue == null || fieldValue.toUpperCase().contains(filterValue.toString().toUpperCase())) {
                                match = true;
                            } else {
                                match = false;
                                break;
                            }
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                        match = false;
                    }
                }
            }

            if (match) {
                books.add(book);
            }
        }

        //sort
        if (!sortBy.isEmpty()) {
            List<Comparator<Book>> comparators = sortBy.values().stream()
                    .map(o -> new LazyBookSorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<Book> cp = ComparatorUtils.chainedComparator(comparators); // from apache
            books.sort(cp);
        }

        //rowCount
        int booksSize = books.size();
        this.setRowCount(booksSize);

        //paginate
        if (booksSize > pageSize) {
            try {
                return books.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return books.subList(first, first + (booksSize % pageSize));
            }
        } else {
            return books;
        }
    }
}
