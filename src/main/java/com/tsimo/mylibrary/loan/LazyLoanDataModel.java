package com.tsimo.mylibrary.loan;

import com.tsimo.mylibrary.entities.Loan;
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
public class LazyLoanDataModel extends LazyDataModel<Loan> {

    private final List<Loan> datasource;

    public LazyLoanDataModel(List<Loan> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Loan getRowData(String rowKey) {
        for (Loan loan : datasource) {
            if (loan.getId() == Integer.parseInt(rowKey)) {
                return loan;
            }
        }
        return null;
    }

    @Override
    public String getRowKey(Loan loan) {
        return String.valueOf(loan.getId());
    }

    @Override
    public List<Loan> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        List<Loan> loans = new ArrayList<>();

        for (Loan loan : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String fieldValue;
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty).getFilterValue();

                        if (filterProperty.equalsIgnoreCase("book.title")) {
                            fieldValue = loan.getBook().getTitle();
                        }
                        else if (filterProperty.equalsIgnoreCase("libuser.email")) {
                            fieldValue = loan.getLibuser().getEmail();
                        } else {
                            Field privateMember = loan.getClass().getDeclaredField(filterProperty);
                            privateMember.setAccessible(true);
                            fieldValue = String.valueOf(privateMember.get(loan));
                        }

                        if (filterValue == null || fieldValue.toUpperCase().contains(filterValue.toString().toUpperCase())) {
                            match = true;
                        } else {
                            match = false;
                            break;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
                        match = false;
                    }
                }
            }

            if (match) {
                loans.add(loan);
            }
        }

        //sort
        if (!sortBy.isEmpty()) {
            List<Comparator<Loan>> comparators = sortBy.values().stream()
                    .map(o -> new LazyLoanSorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<Loan> cp = ComparatorUtils.chainedComparator(comparators); // from apache
            loans.sort(cp);
        }

        //rowCount
        int loansSize = loans.size();
        this.setRowCount(loansSize);

        //paginate
        if (loansSize > pageSize) {
            try {
                return loans.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return loans.subList(first, first + (loansSize % pageSize));
            }
        } else {
            return loans;
        }
    }
}
