package com.tsimo.mylibrary.loan;

import com.tsimo.mylibrary.config.Configuration;
import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.Libuser;
import com.tsimo.mylibrary.entities.Loan;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.util.Calendar;
import java.util.Date;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Tsimo
 */
@Named(value = "loanManagedBean")
@RequestScoped
public class LoanManagedBean {
    
    @Inject
    Configuration configuration;
    @Inject
    private LibrarySessionBean librarySessionBean;
    private String email;
    private Long bookId;
    
    public LoanManagedBean() {
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String createLoan() {
        try {
            Libuser user = librarySessionBean.findUserByEmail(email);
            Book book = librarySessionBean.findBookById(bookId);
            if (book.getAvailableCopies() == 0) {
                Message.addWarnMessage("No available Copies", book.getAvailability().getLabel());
                return "/librarian/createLoan.xhtml?faces-redirect=true";
            }

            if(librarySessionBean.findUserOpenLoans(user.getId()).size() == configuration.getLoanQuantity()) {
                Message.addWarnMessage("Book Limit", "Borrowed Book Limit has been reached");
                return "/librarian/loans.xhtml?faces-redirect=true";
            }
            Date dateNow = new Date();//java.util.Calendar.getInstance().getTime();
            
            Calendar c = Calendar.getInstance();
            c.setTime(dateNow);
            c.add(Calendar.DATE, configuration.getLoanDays());
            Date dateDue = c.getTime();
            
            Loan loan = new Loan();
            loan.setLibuser(user);
            loan.setBook(book);
            loan.setDateCheckout(dateNow);
            loan.setDateDue(dateDue);
            librarySessionBean.create(loan);
            
            int temp = book.getAvailableCopies() -1;
            book.setAvailableCopies( (short) temp );
            librarySessionBean.update(book);
            Message.addSuccessMessage("Loan Created");
            return "/librarian/loans.xhtml?faces-redirect=true";
        } catch (Exception e) {
            Message.addErrorMessage("Loan Creation Failed");
            return "/librarian/createLoan.xhtml?faces-redirect=true";
        }
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
