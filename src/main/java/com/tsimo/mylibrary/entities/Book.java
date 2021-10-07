package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.book.BookAvailability;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tsimo
 */
@Entity
@Table(name = "BOOK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :id"),
    @NamedQuery(name = "Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title = :title"),
    @NamedQuery(name = "Book.findByCopies", query = "SELECT b FROM Book b WHERE b.copies = :copies"),
    @NamedQuery(name = "Book.findByAvailableCopies", query = "SELECT b FROM Book b WHERE b.availableCopies = :availableCopies")})
public class Book implements Serializable {

    @Size(max = 255)
    @Column(name = "IMAGEPATH")
    private String imagepath;
    //Cascade Type deletes the Ba when Book is Deleted
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book", fetch = FetchType.EAGER)
    private List<BookAuthor> bookAuthorList;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="BOOK_ID_SEQ")
    @SequenceGenerator(sequenceName = "BOOK_ID_SEQ", allocationSize = 1, name = "BOOK_ID_SEQ")
    //@NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COPIES")
    private short copies;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AVAILABLE_COPIES")
    private short availableCopies;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "book")
    private List<Loan> loanList;
    @Transient
    private BookAvailability availability;

    public BookAvailability getAvailability() {
        this.availability = new BookAvailability(this);
        return this.availability;
    }

    public Book() {
    }

    public Book(Long id) {
        this.id = id;
    }

    public Book(Long id, String title, short copies, short availableCopies) {
        this.id = id;
        this.title = title;
        this.copies = copies;
        this.availableCopies = availableCopies;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public short getCopies() {
        return copies;
    }

    public void setCopies(short copies) {
        this.copies = copies;
    }

    public short getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(short availableCopies) {
        this.availableCopies = availableCopies;
    }

    @XmlTransient
    public List<Loan> getLoanList() {
        return loanList;
    }

    public void setLoanList(List<Loan> loanList) {
        this.loanList = loanList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tsimo.mylibrary.entities.Book[ id=" + id + " ]";
    }

    @XmlTransient
    public List<BookAuthor> getBookAuthorList() {
        Collections.sort(this.bookAuthorList);
        return bookAuthorList;
    }

    public void setBookAuthorList(List<BookAuthor> bookAuthorList) {
        this.bookAuthorList = bookAuthorList;
    }

    @XmlTransient
    public String getBookAuthorListToString() {
        return getBookAuthorList().toString().replace("[", "").replace("]", "");
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}
