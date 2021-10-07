package com.tsimo.mylibrary.book;

import com.tsimo.mylibrary.config.Configuration;
import com.tsimo.mylibrary.entities.Author;
import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Tsimo
 */
@Named(value = "bookManagedBean")
@RequestScoped
public class BookManagedBean {

    @Inject
    Configuration configuration;
    @Inject
    private LibrarySessionBean librarySessionBean;
    private Book book;
    private List<String> authorNames;
    private UploadedFile file;

    public BookManagedBean() {
    }

    @PostConstruct
    private void init() {
        book = new Book();
        authorNames = new ArrayList<>();
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public List<String> getAuthorNames() {
        return authorNames;
    }

    public void setAuthorNames(List<String> authorNames) {
        this.authorNames = authorNames;
    }

    public String createBook() {
        try {
            if (file == null) {
                Message.addErrorMessage("Error with Image File");
                return "/librarian/createBook.xhtml?faces-redirect=true";
            }
            this.book.setImagepath(configuration.getImageParentPath() + file.getFileName()); //TODO Save /image/ to application Bean, get from Library Settings in DB
            this.book.setAvailableCopies(this.book.getCopies());
            book = librarySessionBean.createAndGetBook(book);
            BookUtilities.createBookAuthors(librarySessionBean, book, authorNames);
            Message.addSuccessMessage("Book Created");
            return "/books.xhtml?faces-redirect=true";
        } catch (Exception e) {
            Message.addErrorMessage("Book Creation Failed");
            return "/librarian/createBook.xhtml?faces-redirect=true";
        }
    }

    public List<String> completeText(String query) {
        List<Author> existingAuthors = librarySessionBean.findAllAuthors();
        String queryLowerCase = query.toLowerCase();
        List<String> authorList = new ArrayList<>();
        existingAuthors.forEach(author -> {
            authorList.add(author.getName());
        });
        return authorList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

}
