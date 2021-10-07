package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.entities.BookAuthor;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-10-07T13:32:32")
@StaticMetamodel(Author.class)
public class Author_ { 

    public static volatile ListAttribute<Author, BookAuthor> bookAuthorList;
    public static volatile SingularAttribute<Author, String> name;
    public static volatile SingularAttribute<Author, Long> id;

}