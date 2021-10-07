package com.tsimo.mylibrary.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tsimo
 */
@Embeddable
public class BookAuthorPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "BOOK_ID")
    private long bookId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUTHOR_ID")
    private long authorId;

    public BookAuthorPK() {
    }

    public BookAuthorPK(long bookId, long authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) bookId;
        hash += (int) authorId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookAuthorPK)) {
            return false;
        }
        BookAuthorPK other = (BookAuthorPK) object;
        if (this.bookId != other.bookId) {
            return false;
        }
        return this.authorId == other.authorId;
    }

    @Override
    public String toString() {
        return "com.tsimo.mylibrary.entities.BookAuthorPK[ bookId=" + bookId + ", authorId=" + authorId + " ]";
    }
    
}
