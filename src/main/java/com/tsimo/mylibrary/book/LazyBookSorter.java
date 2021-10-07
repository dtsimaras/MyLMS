package com.tsimo.mylibrary.book;

import com.tsimo.mylibrary.entities.Book;
import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Tsimo
 */
class LazyBookSorter implements Comparator<Book> {

    private final String sortField;
    private final SortOrder sortOrder;

    LazyBookSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Book book1, Book book2) {
        try {
            int value;
            if (sortField.equalsIgnoreCase("availability.availability")) {
                String value1 = book1.getAvailability().getAvailability().toString();
                String value2 = book2.getAvailability().getAvailability().toString();
                value = ((Comparable) value1).compareTo(value2);
            } else if (sortField.equalsIgnoreCase("bookAuthorListToString")) {
                String value1 = book1.getBookAuthorListToString();
                String value2 = book2.getBookAuthorListToString();
                value = ((Comparable) value1).compareTo(value2);
            } else {
                Field privateMember1 = book1.getClass().getDeclaredField(sortField);
                privateMember1.setAccessible(true);
                String value1 = String.valueOf(privateMember1.get(book1));

                Field privateMember2 = book2.getClass().getDeclaredField(sortField);
                privateMember2.setAccessible(true);
                String value2 = String.valueOf(privateMember2.get(book2));

                if (privateMember1.getType() == Long.class
                        || privateMember1.getType() == short.class) {
                    int int1 = Integer.parseInt(value1);
                    int int2 = Integer.parseInt(value2);
                    value = ((Comparable) int1).compareTo(int2);
                } else {
                    value = ((Comparable) value1).compareTo(value2);
                }
            }

            return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e);
        }
    }

}
