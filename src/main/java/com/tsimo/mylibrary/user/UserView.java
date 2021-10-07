package com.tsimo.mylibrary.user;

import com.tsimo.mylibrary.entities.Libuser;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.DatatypeConverter;
import org.primefaces.model.LazyDataModel;

/**
 *
 * @author Tsimo
 */
@Named(value = "userView")
@ViewScoped
public class UserView implements Serializable {

    @Inject
    LibrarySessionBean librarySessionBean;
    LazyDataModel lazyModel;
    private Libuser selectedUser;

    public UserView() {
    }

    @PostConstruct
    private void init() {
        lazyModel = new LazyUserDataModel(librarySessionBean.findAllUsers());
    }

    public LazyDataModel<Libuser> getLazyModel() {
        return lazyModel;
    }

    public Libuser getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Libuser selectedUser) {
        this.selectedUser = selectedUser;
    }

    public String updateUser() {
        try {
            selectedUser.setPassword(hashPassword(selectedUser.getPassword()));
            librarySessionBean.update(selectedUser);
            Message.addSuccessMessage("User Updated");
        } catch (Exception e) {
            Message.addErrorMessage("User Update Failed");
        }
        selectedUser = null;
        return "/librarian/users.xhtml?faces-redirect=true";
    }

    public String deleteUser() {
        try {
            if (librarySessionBean.findUserOpenLoans(selectedUser.getId()).size() > 0) {
                Message.addErrorMessage("User Deletion Failed", "Open Loan Exist");
                return "/librarian/users.xhtml?faces-redirect=true";
            }
            librarySessionBean.delete(selectedUser);
            Message.addSuccessMessage("Used Deleted");
        } catch (Exception e) {
            Message.addErrorMessage("User Deletion Failed");
        }
        selectedUser = null;
        return "/librarian/users.xhtml?faces-redirect=true";
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        byte[] hashBytes = MessageDigest.getInstance("SHA-256")
                .digest(password.getBytes(StandardCharsets.UTF_8));
        String hash = DatatypeConverter.printHexBinary(hashBytes);
        return hash;
    }
}
