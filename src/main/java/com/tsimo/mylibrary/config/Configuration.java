package com.tsimo.mylibrary.config;

import com.tsimo.mylibrary.entities.Libconfig;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 *
 * @author Tsimo
 */
//@Named(value = "configuration")
@ApplicationScoped
public class Configuration {

    @Inject
    LibrarySessionBean librarySessionBean;
    Libconfig libConfig;
    private int loanDays;
    private int loanQuantity;
    private String imageParentPath;

    public Configuration() {
    }

    @PostConstruct
    void updateConfig() {
        libConfig = librarySessionBean.getLibConfig();
        loanDays = libConfig.getLoandays();
        loanQuantity = libConfig.getLoanquantity();
        imageParentPath = libConfig.getImageparentpath();
    }

    public int getLoanDays() {
        return loanDays;
    }

    public int getLoanQuantity() {
        return loanQuantity;
    }

    public String getImageParentPath() {
        return imageParentPath;
    }
}
