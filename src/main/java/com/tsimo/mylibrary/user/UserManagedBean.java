package com.tsimo.mylibrary.user;

import com.tsimo.mylibrary.entities.Libuser;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author Tsimo
 */
@Named(value = "userManagedBean")
@RequestScoped
public class UserManagedBean {

    @Inject
    private LibrarySessionBean librarySessionBean;
    private Libuser user;
    private String verifyPassword;

    public UserManagedBean() {
    }

    @PostConstruct
    private void init() {
        user = new Libuser();
    }

    public Libuser getUser() {
        return user;
    }

    public void setUser(Libuser user) {
        this.user = user;
    }

    public String createUser() {
        try {
            if(!user.getPassword().equals(verifyPassword)) {
                Message.addWarnMessage("Passwords don't Match");
                return "/librarian/createUser.xhtml?faces-redirect=true";
            }
            //TODO validations no /' allowed message
            user.setPassword(hashPassword(verifyPassword));
            user.setRole("member");
            librarySessionBean.create(user);
            Message.addSuccessMessage("User Created");
            return "/librarian/users.xhtml?faces-redirect=true";
        } catch (Exception e) {
            Message.addErrorMessage("User Creation Failed");
            return "/librarian/createUser.xhtml?faces-redirect=true";
        }
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }
    
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] hashBytes = MessageDigest.getInstance("SHA-256")
                .digest(password.getBytes(StandardCharsets.UTF_8));
        String hash = DatatypeConverter.printHexBinary(hashBytes);
        return hash;
    }
}
