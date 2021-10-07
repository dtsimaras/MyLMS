package com.tsimo.mylibrary.user;

import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.Libuser;
import java.lang.reflect.Field;
import java.util.Comparator;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Tsimo
 */
public class LazyUserSorter implements Comparator<Libuser> {

    private final String sortField;
    private final SortOrder sortOrder;

    LazyUserSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Libuser user1, Libuser user2) {
        try {
            int value;
            Field privateMember1 = user1.getClass().getDeclaredField(sortField);
            privateMember1.setAccessible(true);
            String value1 = String.valueOf(privateMember1.get(user1));

            Field privateMember2 = user2.getClass().getDeclaredField(sortField);
            privateMember2.setAccessible(true);
            String value2 = String.valueOf(privateMember2.get(user2));

            if (privateMember1.getType() == Long.class
                    || privateMember1.getType() == short.class) {
                int int1 = Integer.parseInt(value1);
                int int2 = Integer.parseInt(value2);
                value = ((Comparable) int1).compareTo(int2);
            } else {
                value = ((Comparable) value1).compareTo(value2);
            }

        return SortOrder.ASCENDING.equals(sortOrder) ? value : -1 * value;
    }
    catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e

    
        ) {
            throw new RuntimeException(e);
    }
}
}
