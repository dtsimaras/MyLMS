package com.tsimo.mylibrary.book;

import com.tsimo.mylibrary.entities.Author;
import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.BookAuthor;
import com.tsimo.mylibrary.entities.BookAuthorPK;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Tsimo
 */
public class BookUtilities {

    static void createBookAuthors(LibrarySessionBean librarySessionBean, Book book, List<String> names) {
        try {
            //create Authors If Not Exist
            List<Author> existingAuthors = librarySessionBean.findAllAuthors();
            List<Author> selectedBookAuthors = new ArrayList<>();
            for (String authorName : names) {
                if (!existingAuthors.stream().filter(Objects::nonNull).anyMatch(o -> o.getName().equals(authorName.trim()))) {
                    librarySessionBean.create(new Author(authorName.trim()));
                }
            }
            //reload Authors
            existingAuthors = librarySessionBean.findAllAuthors();
            //find this.book's Authors
            for (String authorName : names) {
                for (Author existingAuthor : existingAuthors) {
                    if (existingAuthor.getName().equals(authorName.trim())) {
                        selectedBookAuthors.add(existingAuthor);
                    }
                }
            }
            //delete previous BookAuthors
            librarySessionBean.deleteBookAuthors(book.getId());
            //insert new BookAuthors
            BookAuthor bookAuthor;
            BookAuthorPK bookAuthorPK;
            List<BookAuthor> bookAuthors = new ArrayList<>();
            short i = 1;
            for (Author author : selectedBookAuthors) {
                bookAuthorPK = new BookAuthorPK(book.getId(), author.getId());
                bookAuthor = new BookAuthor(bookAuthorPK, i);
                bookAuthor.setAuthor(author);
                bookAuthor.setBook(book);
                librarySessionBean.update(bookAuthor);
                bookAuthors.add(bookAuthor);
                i++;
            }
            book.setBookAuthorList(bookAuthors);
            librarySessionBean.update(book);
            //Message.addSuccessMessage("Book's Authors Created");
        } catch (Exception e) {
            Message.addErrorMessage("Book's Authors Creation Failed");
        }
    }
}
