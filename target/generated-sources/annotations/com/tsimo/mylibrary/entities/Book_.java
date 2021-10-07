package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.entities.BookAuthor;
import com.tsimo.mylibrary.entities.Loan;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-10-07T13:32:32")
@StaticMetamodel(Book.class)
public class Book_ { 

    public static volatile SingularAttribute<Book, Short> copies;
    public static volatile SingularAttribute<Book, String> imagepath;
    public static volatile ListAttribute<Book, Loan> loanList;
    public static volatile ListAttribute<Book, BookAuthor> bookAuthorList;
    public static volatile SingularAttribute<Book, Short> availableCopies;
    public static volatile SingularAttribute<Book, Long> id;
    public static volatile SingularAttribute<Book, String> title;

}