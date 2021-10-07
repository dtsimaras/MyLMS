package com.tsimo.mylibrary.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Tsimo
 */ 
@Entity
@Table(name = "LIBCONFIG")
@NamedQueries({
    @NamedQuery(name = "Libconfig.findAll", query = "SELECT l FROM Libconfig l"),
    @NamedQuery(name = "Libconfig.findById", query = "SELECT l FROM Libconfig l WHERE l.id = :id"),
    @NamedQuery(name = "Libconfig.findByLoandays", query = "SELECT l FROM Libconfig l WHERE l.loandays = :loandays"),
    @NamedQuery(name = "Libconfig.findByLoanquantity", query = "SELECT l FROM Libconfig l WHERE l.loanquantity = :loanquantity"),
    @NamedQuery(name = "Libconfig.findByImageparentpath", query = "SELECT l FROM Libconfig l WHERE l.imageparentpath = :imageparentpath")})
public class Libconfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOANDAYS")
    private short loandays;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LOANQUANTITY")
    private short loanquantity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "IMAGEPARENTPATH")
    private String imageparentpath;

    public Libconfig() {
    }

    public Libconfig(Short id) {
        this.id = id;
    }

    public Libconfig(Short id, short loandays, short loanquantity, String imageparentpath) {
        this.id = id;
        this.loandays = loandays;
        this.loanquantity = loanquantity;
        this.imageparentpath = imageparentpath;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public short getLoandays() {
        return loandays;
    }

    public void setLoandays(short loandays) {
        this.loandays = loandays;
    }

    public short getLoanquantity() {
        return loanquantity;
    }

    public void setLoanquantity(short loanquantity) {
        this.loanquantity = loanquantity;
    }

    public String getImageparentpath() {
        return imageparentpath;
    }

    public void setImageparentpath(String imageparentpath) {
        this.imageparentpath = imageparentpath;
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
        if (!(object instanceof Libconfig)) {
            return false;
        }
        Libconfig other = (Libconfig) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tsimo.mylibrary.loan.Libconfig[ id=" + id + " ]";
    }
    
}
