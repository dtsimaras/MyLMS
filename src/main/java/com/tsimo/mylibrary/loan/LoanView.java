package com.tsimo.mylibrary.loan;

import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.Loan;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Tsimo
 */
@Named(value = "loanView")
@ViewScoped
public class LoanView implements Serializable {

    @Inject
    LibrarySessionBean librarySessionBean;
    private Loan selectedLoan;
    private LazyDataModel lazyModel;

    public LoanView() {
    }

    @PostConstruct
    private void init() {
        lazyModel = new LazyLoanDataModel(librarySessionBean.findAllLoans());
    }

    public LazyDataModel getLazyModel() {
        return lazyModel;
    }

    public Loan getSelectedLoan() {
        return selectedLoan;
    }

    public void setSelectedLoan(Loan selectedLoan) {
        this.selectedLoan = selectedLoan;
    }

    public LoanStatus[] getStatusValues() {
        return LoanStatus.values();
    }

    public String updateLoanStatus() {
        try {
            Date dateNow = new Date();//java.util.Calendar.getInstance().getTime();
            selectedLoan.setDateCheckin(dateNow);
            librarySessionBean.update(selectedLoan);

            Book book = selectedLoan.getBook();
            book.setAvailableCopies((short) (book.getAvailableCopies() + 1));
            librarySessionBean.update(book);

            Message.addSuccessMessage("Loan Updated", "Book has been returned");
        } catch (Exception e) {
            Message.addErrorMessage("Loan Update Failed");
        }
        selectedLoan = null;
        return "/librarian/loans.xhtml?faces-redirect=true";
    }
    
    public String deleteLoan() {
        try {
            librarySessionBean.delete(selectedLoan);
            Message.addSuccessMessage("Loan Deleted");
        }
        catch (Exception e) {
            Message.addErrorMessage("Loan Deletion Failed");
        }
        selectedLoan = null;
        return "/librarian/loans.xhtml?faces-redirect=true";
    }
}
