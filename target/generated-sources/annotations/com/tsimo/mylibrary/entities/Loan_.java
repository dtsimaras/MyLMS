package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.entities.Book;
import com.tsimo.mylibrary.entities.Libuser;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-10-07T13:32:32")
@StaticMetamodel(Loan.class)
public class Loan_ { 

    public static volatile SingularAttribute<Loan, Date> dateDue;
    public static volatile SingularAttribute<Loan, Date> dateCheckin;
    public static volatile SingularAttribute<Loan, Book> book;
    public static volatile SingularAttribute<Loan, Libuser> libuser;
    public static volatile SingularAttribute<Loan, Long> id;
    public static volatile SingularAttribute<Loan, Date> dateCheckout;

}