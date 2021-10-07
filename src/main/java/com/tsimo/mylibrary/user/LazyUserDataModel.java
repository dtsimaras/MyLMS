package com.tsimo.mylibrary.user;

import com.tsimo.mylibrary.entities.Libuser;
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
public class LazyUserDataModel extends LazyDataModel<Libuser> {

    private final List<Libuser> datasource;

    public LazyUserDataModel(List<Libuser> datasource) {
        this.datasource = datasource;
    }

    @Override
    public Libuser getRowData(String rowKey) {
        for (Libuser user : datasource) {
            if (user.getId() == Integer.parseInt(rowKey)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public String getRowKey(Libuser user) {
        return String.valueOf(user.getId());
    }

    @Override
    public List<Libuser> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filters) {
        List<Libuser> users = new ArrayList<>();

        for (Libuser user : datasource) {
            boolean match = true;

            if (filters != null) {
                for (Iterator<String> it = filters.keySet().iterator(); it.hasNext();) {
                    try {
                        String fieldValue;
                        String filterProperty = it.next();
                        Object filterValue = filters.get(filterProperty).getFilterValue();

                        Field privateMember = user.getClass().getDeclaredField(filterProperty);
                        privateMember.setAccessible(true);
                        fieldValue = String.valueOf(privateMember.get(user));

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
                users.add(user);
            }
        }

        //sort
        if (!sortBy.isEmpty()) {
            List<Comparator<Libuser>> comparators = sortBy.values().stream()
                    .map(o -> new LazyUserSorter(o.getField(), o.getOrder()))
                    .collect(Collectors.toList());
            Comparator<Libuser> cp = ComparatorUtils.chainedComparator(comparators); // from apache
            users.sort(cp);
        }
        //rowCount
        int usersSize = users.size();
        this.setRowCount(usersSize);

        //paginate
        if (usersSize > pageSize) {
            try {
                return users.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return users.subList(first, first + (usersSize % pageSize));
            }
        } else {
            return users;
        }
    }
}
