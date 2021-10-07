package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.loan.LoanStatus;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tsimo
 */
@Entity
@Table(name = "LOAN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Loan.findAll", query = "SELECT l FROM Loan l"),
    @NamedQuery(name = "Loan.findById", query = "SELECT l FROM Loan l WHERE l.id = :id"),
    @NamedQuery(name = "Loan.findByDateCheckout", query = "SELECT l FROM Loan l WHERE l.dateCheckout = :dateCheckout"),
    @NamedQuery(name = "Loan.findByDateDue", query = "SELECT l FROM Loan l WHERE l.dateDue = :dateDue"),
    @NamedQuery(name = "Loan.findByDateCheckin", query = "SELECT l FROM Loan l WHERE l.dateCheckin = :dateCheckin"),
    @NamedQuery(name = "Loan.findByUserId", query = "SELECT l FROM Loan l WHERE l.libuser.id = :userid"),
    @NamedQuery(name = "Loan.findOpenByUserId", query = "SELECT l FROM Loan l WHERE (l.libuser.id = :userid AND l.dateCheckin IS NULL)"),
    @NamedQuery(name = "Loan.findClosedByUserId", query = "SELECT l FROM Loan l WHERE (l.libuser.id = :userid AND l.dateCheckin IS NOT NULL)"),
    @NamedQuery(name = "Loan.findOpenByBookId", query = "SELECT l FROM Loan l WHERE (l.book.id = :bookid AND l.dateCheckin IS NULL)")})
public class Loan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    //@NotNull
    @Column(name = "ID")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_CHECKOUT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCheckout;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATE_DUE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDue;
    @Column(name = "DATE_CHECKIN")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCheckin;
    @JoinColumn(name = "BOOK_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Book book;
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Libuser libuser;
    @Transient
    private LoanStatus status;

    public LoanStatus getStatus() {
        prepareStatus(); //Remove this logic from getter
        return this.status;
    }

    public void prepareStatus() {
        Date date = java.util.Calendar.getInstance().getTime();
        if (this.dateCheckin == null && this.dateDue.after(date)) {
            this.status = LoanStatus.OPEN;
        } else if (this.dateCheckin == null && this.dateDue.before(date)) {
            this.status = LoanStatus.DELAYED;
        } else {
            this.status = LoanStatus.CLOSED;
        }
    }

    public Loan() {
    }

    public Loan(Long id) {
        this.id = id;
    }

    public Loan(Long id, Date dateCheckout, Date dateDue) {
        this.id = id;
        this.dateCheckout = dateCheckout;
        this.dateDue = dateDue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateCheckout() {
        return dateCheckout;
    }

    public void setDateCheckout(Date dateCheckout) {
        this.dateCheckout = dateCheckout;
    }

    public Date getDateDue() {
        return dateDue;
    }

    public void setDateDue(Date dateDue) {
        this.dateDue = dateDue;
    }

    public Date getDateCheckin() {
        return dateCheckin;
    }

    public void setDateCheckin(Date dateCheckin) {
        this.dateCheckin = dateCheckin;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Libuser getLibuser() {
        return libuser;
    }

    public void setLibuser(Libuser libuser) {
        this.libuser = libuser;
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
        if (!(object instanceof Loan)) {
            return false;
        }
        Loan other = (Loan) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "com.tsimo.mylibrary.entities.Loan[ id=" + id + " ]";
    }

}
