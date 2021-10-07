package com.tsimo.mylibrary.book;

import com.tsimo.mylibrary.config.Configuration;
import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Tsimo
 */
@Named(value = "bookView")
@ViewScoped
public class BookView implements Serializable {

    @Inject
    Configuration configuration;
    @Inject
    private LibrarySessionBean librarySessionBean;
    private Book selectedBook;
    private List<String> authorNames;
    private short previousBookCopies;
    private LazyDataModel<Book> lazyModel;
    private UploadedFile file;

    public BookView() {
    }

    @PostConstruct
    private void init() {
        lazyModel = new LazyBookDataModel(librarySessionBean.findAllBooks());
    }

    public LazyDataModel<Book> getLazyModel() {
        return lazyModel;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }

    public void setSelectedBook(Book selectedBook) {
        this.selectedBook = selectedBook;
        previousBookCopies = selectedBook.getCopies();
    }

    public List<String> getAuthorNames() {
        authorNames = new ArrayList();
        selectedBook.getBookAuthorList().forEach(bookAuthor -> {
            authorNames.add(bookAuthor.getAuthor().getName());
        });
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public BookAvailabilityEnum[] getAvailabilityValues() {
        return BookAvailabilityEnum.values();
    }

    public String updateBook() {
        try {
            if (file != null) {
                selectedBook.setImagepath(configuration.getImageParentPath() + file.getFileName()); //TODO fix up path
            }
            int temp = selectedBook.getAvailableCopies() + (selectedBook.getCopies() - previousBookCopies);
            if (temp < 0) {
                int loanedCopies = selectedBook.getCopies() - selectedBook.getAvailableCopies();
                Message.addWarnMessage("Error with Copies", "Book copies must be more than the loaned ones (" + loanedCopies + ")");
            }
            short availableCopies = (short) temp;
            selectedBook.setAvailableCopies(availableCopies);
            BookUtilities.createBookAuthors(librarySessionBean, selectedBook, authorNames);
            file = null;
            Message.addSuccessMessage("Book Updated");
        } catch (Exception e) {
            Message.addErrorMessage("Book Update Failed");
        }
        selectedBook = null;
        previousBookCopies = 0;
        return "/books.xhtml?faces-redirect=true";
    }

    public String deleteBook() {
        try {
            if (librarySessionBean.openLoanExists(selectedBook.getId())) {
                Message.addErrorMessage("Book Deletion Failed", "Open Loan Exists");
                return "/books.xhtml?faces-redirect=true";
            }
            librarySessionBean.delete(selectedBook);
            selectedBook = null;
            Message.addSuccessMessage("Book Deleted");
        } catch (Exception e) {
            Message.addErrorMessage("Book Deletion Failed");
        }
        return "/books.xhtml?faces-redirect=true";
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }
}
