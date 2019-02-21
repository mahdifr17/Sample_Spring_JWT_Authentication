package com.mahdifr.sample_spring_jwt_authentication.security.registration;

import java.util.Locale;

import com.mahdifr.sample_spring_jwt_authentication.model.UserModel;

import org.springframework.context.ApplicationEvent;

/**
 * OnRegistrationCompleteEvent
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserModel user;

    public OnRegistrationCompleteEvent(String appUrl, Locale locale, UserModel user) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }

    /**
     * @return the appUrl
     */
    public String getAppUrl() {
        return appUrl;
    }
    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }/**
     * @return the user
     */
    public UserModel getUser() {
        return user;
    }

    /**
     * @param appUrl the appUrl to set
     */
    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }
    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserModel user) {
        this.user = user;
    }
}