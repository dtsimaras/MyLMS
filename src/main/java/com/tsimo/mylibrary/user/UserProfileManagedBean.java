package com.tsimo.mylibrary.user;

import com.tsimo.mylibrary.entities.Libuser;
import com.tsimo.mylibrary.entities.Loan;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tsimo
 */
@Named(value = "userProfileManagedBean")
@ViewScoped
public class UserProfileManagedBean implements Serializable {

    @Inject
    LibrarySessionBean librarySessionBean;
    List<Loan> openLoans;
    List<Loan> closedLoans;
    private String usersname;

    public UserProfileManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            String username = request.getRemoteUser();
            Libuser user = librarySessionBean.findUserByEmail(username);
            usersname = user.getName();
            openLoans = librarySessionBean.findUserOpenLoans(user.getId());
            closedLoans = librarySessionBean.findUserClosedLoans(user.getId());
        } catch (Exception e) {
            Message.addErrorMessage("Something went wrong", "Please contact us so we can solve it!");
        }
    }

    public String getUsersname() {
        return usersname;
    }

    public List<Loan> getOpenLoans() {
        return openLoans;
    }

    public List<Loan> getClosedLoans() {
        return closedLoans;
    }

}
