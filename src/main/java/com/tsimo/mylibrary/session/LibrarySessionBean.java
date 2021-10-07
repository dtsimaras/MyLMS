package com.tsimo.mylibrary.session;

import com.tsimo.mylibrary.entities.Author;
import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.BookAuthor;
import com.tsimo.mylibrary.entities.Libconfig;
import com.tsimo.mylibrary.entities.Libuser;
import com.tsimo.mylibrary.entities.Loan;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Tsimo
 */
@Stateless
public class LibrarySessionBean {

    @PersistenceContext(unitName = "com.tsimo_myLibrary_v0.1_war_0.1PU")
    private EntityManager em;

    public void create(Object o) {
        em.persist(o);
        em.flush();
    }

    public void update(Object o) {
        em.merge(o);
        em.flush();
    }

    public void delete(Object o) {
        em.remove(em.merge(o));
        em.flush();
    }

    public List<Book> findAllBooks() {
        return em.createNamedQuery("Book.findAll").getResultList();
    }

    public List<Author> findAllAuthors() {
        return em.createNamedQuery("Author.findAll").getResultList();
    }

    public List<Loan> findAllLoans() {
        return em.createNamedQuery("Loan.findAll").getResultList();
    }

    public List<Libuser> findAllUsers() {
        return em.createNamedQuery("Libuser.findAll").getResultList();
    }

    //LibConfig
    public Libconfig getLibConfig() {
        return (Libconfig) em.find(Libconfig.class, (short) 1);
    }

    // Book Use Cases
    public Book createAndGetBook(Book book) {
        em.persist(book);
        em.flush();
        return book;
    }

    public void deleteBookAuthors(Long id) {
        List<BookAuthor> bookAuthors = em.createNamedQuery("BookAuthor.findByBookId").setParameter("bookId", id).getResultList();
        bookAuthors.forEach(bookAuthor -> {
            delete(bookAuthor);
        });
    }
    
    public boolean openLoanExists(Long id) {
        List<Loan> loans = em.createNamedQuery("Loan.findOpenByBookId").setParameter("bookid", id).getResultList();
        return (loans.size() > 0);
    }

    // Loan Use Cases
    public Libuser findUserByEmail(String email) {
        return (Libuser) em.createNamedQuery("Libuser.findByEmail").setParameter("email", email).getSingleResult();
    }

    public Book findBookById(Long id) {
        return (Book) em.createNamedQuery("Book.findById").setParameter("id", id).getSingleResult();
    }

    // & Profile Use Case
    public List<Loan> findUserLoans(Long id) {
        return em.createNamedQuery("Loan.findByUserId").setParameter("userid", id).getResultList();
    }

    public List<Loan> findUserOpenLoans(Long id) {
        return em.createNamedQuery("Loan.findOpenByUserId").setParameter("userid", id).getResultList();
    }

    public List<Loan> findUserClosedLoans(Long id) {
        return em.createNamedQuery("Loan.findClosedByUserId").setParameter("userid", id).getResultList();
    }

}
