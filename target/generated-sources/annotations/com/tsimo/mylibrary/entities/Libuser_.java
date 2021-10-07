package com.tsimo.mylibrary.entities;

import com.tsimo.mylibrary.entities.Loan;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-10-07T13:32:32")
@StaticMetamodel(Libuser.class)
public class Libuser_ { 

    public static volatile SingularAttribute<Libuser, String> password;
    public static volatile SingularAttribute<Libuser, String> role;
    public static volatile ListAttribute<Libuser, Loan> loanList;
    public static volatile SingularAttribute<Libuser, String> name;
    public static volatile SingularAttribute<Libuser, Long> id;
    public static volatile SingularAttribute<Libuser, String> email;
    public static volatile SingularAttribute<Libuser, String> lastname;

}