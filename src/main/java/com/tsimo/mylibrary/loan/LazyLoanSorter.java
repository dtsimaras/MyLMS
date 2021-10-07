package com.tsimo.mylibrary.loan;

import com.tsimo.mylibrary.entities.Loan;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Date;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Tsimo
 */
class LazyLoanSorter implements Comparator<Loan> {

    private final String sortField;
    private final SortOrder sortOrder;

    LazyLoanSorter(String sortField, SortOrder sortOrder) {
        this.sortField = sortField;
        this.sortOrder = sortOrder;
    }

    @Override
    public int compare(Loan loan1, Loan loan2) {
        try {
            int value;
            //String value1, value2;
            if (sortField.equalsIgnoreCase("book.title")) {
                String value1 = loan1.getBook().getTitle();
                String value2 = loan2.getBook().getTitle();
                value = ((Comparable) value1).compareTo(value2);
            } else if (sortField.equalsIgnoreCase("libuser.email")) {
                String value1 = loan1.toString();
                String value2 = loan2.toString();
                value = ((Comparable) value1).compareTo(value2);
            } else {
                Field privateMember1 = loan1.getClass().getDeclaredField(sortField);
                privateMember1.setAccessible(true);
                String value1 = String.valueOf(privateMember1.get(loan1));

                Field privateMember2 = loan2.getClass().getDeclaredField(sortField);
                privateMember2.setAccessible(true);
                String value2 = String.valueOf(privateMember2.get(loan2));

                if (privateMember1.getType() == Date.class) {
                    Date date1 = (Date) privateMember1.get(loan1);
                    Date date2 = (Date) privateMember1.get(loan2);
                    value = ((Comparable) date1).compareTo(date2);
                } else if (privateMember1.getType() == Long.class
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
