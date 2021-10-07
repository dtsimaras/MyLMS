package com.tsimo.mylibrary.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Tsimo
 */
@Entity
@Table(name = "BOOK_AUTHOR")
@NamedQueries({
    @NamedQuery(name = "BookAuthor.findAll", query = "SELECT b FROM BookAuthor b"),
    @NamedQuery(name = "BookAuthor.findByBookId", query = "SELECT b FROM BookAuthor b WHERE b.bookAuthorPK.bookId = :bookId"),
    @NamedQuery(name = "BookAuthor.findByAuthorId", query = "SELECT b FROM BookAuthor b WHERE b.bookAuthorPK.authorId = :authorId"),
    @NamedQuery(name = "BookAuthor.findByArrangement", query = "SELECT b FROM BookAuthor b WHERE b.arrangement = :arrangement")})
public class BookAuthor implements Serializable, Comparable<BookAuthor> {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BookAuthorPK bookAuthorPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ARRANGEMENT")
    private short arrangement;
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Author author;
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Book book;

    public BookAuthor() {
    }

    public BookAuthor(BookAuthorPK bookAuthorPK) {
        this.bookAuthorPK = bookAuthorPK;
    }

    public BookAuthor(BookAuthorPK bookAuthorPK, short arrangement) {
        this.bookAuthorPK = bookAuthorPK;
        this.arrangement = arrangement;
    }

    public BookAuthor(long bookId, long authorId) {
        this.bookAuthorPK = new BookAuthorPK(bookId, authorId);
    }

    public BookAuthorPK getBookAuthorPK() {
        return bookAuthorPK;
    }

    public void setBookAuthorPK(BookAuthorPK bookAuthorPK) {
        this.bookAuthorPK = bookAuthorPK;
    }

    public short getArrangement() {
        return arrangement;
    }

    public void setArrangement(short arrangement) {
        this.arrangement = arrangement;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookAuthorPK != null ? bookAuthorPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BookAuthor)) {
            return false;
        }
        BookAuthor other = (BookAuthor) object;
        return !((this.bookAuthorPK == null && other.bookAuthorPK != null) || (this.bookAuthorPK != null && !this.bookAuthorPK.equals(other.bookAuthorPK)));
    }

    @Override
    public String toString() {
        //return "com.tsimo.mylibrary.entities.BookAuthor[ bookAuthorPK=" + bookAuthorPK + " ]";
        return getAuthor().getName();
    }

    @Override
    public int compareTo(BookAuthor ba) {
        Short thisBookAuthorOrder = this.getArrangement();
        return thisBookAuthorOrder.compareTo(ba.getArrangement());
    }

}
