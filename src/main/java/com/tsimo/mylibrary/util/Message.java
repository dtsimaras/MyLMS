package com.tsimo.mylibrary.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Tsimo
 */
public class Message {

    public static void addSuccessMessage(String msg) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, "");
        addMessage(message);
    }

    public static void addSuccessMessage(String msg, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detail);
        addMessage(message);
    }

    public static void addErrorMessage(String msg) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "");
        addMessage(message);
    }

    public static void addErrorMessage(String msg, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, detail);
        addMessage(message);
    }

    public static void addWarnMessage(String msg) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "");
        addMessage(message);
    }

    public static void addWarnMessage(String msg, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, detail);
        addMessage(message);
    }

    private static void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
}
