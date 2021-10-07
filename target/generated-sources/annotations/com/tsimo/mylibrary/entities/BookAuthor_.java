package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.entities.Author;
import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.BookAuthorPK;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-10-07T13:32:32")
@StaticMetamodel(BookAuthor.class)
public class BookAuthor_ { 

    public static volatile SingularAttribute<BookAuthor, Short> arrangement;
    public static volatile SingularAttribute<BookAuthor, Author> author;
    public static volatile SingularAttribute<BookAuthor, Book> book;
    public static volatile SingularAttribute<BookAuthor, BookAuthorPK> bookAuthorPK;

}