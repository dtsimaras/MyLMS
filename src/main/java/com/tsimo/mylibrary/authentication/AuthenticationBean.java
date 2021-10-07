package com.tsimo.mylibrary.authentication;

import com.tsimo.mylibrary.util.Message;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Tsimo
 *
 * UC: login, logout, validate Role, find User
 */
@Named(value = "authenticationBean")
@SessionScoped
public class AuthenticationBean implements Serializable {
    //TODO replace Glassfish security so I am not glassfish depended
    public AuthenticationBean() {
    }

    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //TODO Create Documentation
    /**
     *  Verifies User Credentials and logs in if credentials correct.
     *  Shows Appropriate Message for success and for failure.
     * @return home page
     */
    public String login() {
        try {
            getRequest().login(getUsername(), getPassword());
        } catch (ServletException e) {
            Message.addWarnMessage("Login Failed", "Wrong Credentials");
            username = null;
            password = null;
            return "/login.xhtml?faces-redirect=true";
        }
        return "/member/profile.xhtml?faces-redirect=true";
    }

    public String logout() {
        try {
            getRequest().logout();
            username = null;
            password = null;
        } catch (ServletException e) {
            Message.addErrorMessage("Logout Failed", "Please contact us if you see this message");
        }
        return "/index.xtml?faces-redirect=true";
    }

    public boolean hasLibrarianPrivileges() {
        return getRequest().isUserInRole("librarian") || getRequest().isUserInRole("admin");
    }

    public boolean hasAdminPrivileges() {
        return getRequest().isUserInRole("admin");
    }
    
    private HttpServletRequest getRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        return (HttpServletRequest) context.getExternalContext().getRequest();
    }

}
