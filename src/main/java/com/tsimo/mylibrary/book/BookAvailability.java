package com.tsimo.mylibrary.book;

import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.Loan;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Tsimo
 */
public class BookAvailability {

    private BookAvailabilityEnum availability;
    private String label;
    private final Book book;

    public BookAvailability(Book book) {
        this.book = book;
        init();
    }

    private void init() {
        // 1 Copy Available
        if (book.getAvailableCopies() == 1) {
            this.setAvailability(BookAvailabilityEnum.AVAILABLE);
            this.setLabel(book.getAvailableCopies() + " Available Copy");
            return;
        }
        // Multiple Copies Available
        if (book.getAvailableCopies() > 0) {
            this.setAvailability(BookAvailabilityEnum.AVAILABLE);
            this.setLabel(book.getAvailableCopies() + " Available Copies");
            return;
        }
        
        // Find Closest Available Date
        Date closestDate = new Date(Long.MAX_VALUE);
        Date dateNow = new Date();//java.util.Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        
        for (Loan loan : book.getLoanList()) {
            if (loan.getDateDue().after(dateNow)) {
                if (loan.getDateDue().before(closestDate)) {
                    closestDate = loan.getDateDue();
                }
            }
        }
        
        // Find Expected Return or Unavailable
        if (!closestDate.equals(new Date(Long.MAX_VALUE))) {
            this.setLabel("Expected Return: " + dateFormat.format(closestDate));
            this.setAvailability(BookAvailabilityEnum.EXPECTED);
        } else {
            this.setAvailability(BookAvailabilityEnum.UNAVAILABLE);
            this.setLabel("Not Available");
        }
    }

    public BookAvailabilityEnum getAvailability() {
        return availability;
    }

    public void setAvailability(BookAvailabilityEnum availability) {
        this.availability = availability;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
}
