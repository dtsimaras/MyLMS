package com.tsimo.mylibrary.config;

import com.tsimo.mylibrary.entities.Libconfig;
import com.tsimo.mylibrary.session.LibrarySessionBean;
import com.tsimo.mylibrary.util.Message;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Tsimo
 */
@Named(value = "configManagedBean")
@RequestScoped
public class ConfigManagedBean {

    @Inject
    Configuration configuration;
    @Inject
    LibrarySessionBean librarySessionBean;
    private Libconfig libConfig;

    public ConfigManagedBean() {
    }

    @PostConstruct
    private void init() {
        libConfig = librarySessionBean.getLibConfig();
    }

    public Libconfig getLibConfig() {
        return libConfig;
    }

    public void setLibConfig(Libconfig libConfig) {
        this.libConfig = libConfig;
    }

    public String updateLibConfig() {
        try {
            librarySessionBean.update(libConfig);
            configuration.updateConfig();
            Message.addSuccessMessage("Settings Updated");
        } catch (Exception e) {
            Message.addErrorMessage("Settings Update Failed");
        }
        return "/admin/libConfiguration.xhtml?faces-redirect=true";
    }
}
