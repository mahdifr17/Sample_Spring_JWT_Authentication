package com.mahdifr.sample_spring_jwt_authentication.payload;

import javax.validation.constraints.NotBlank;

/**
 * LoginRequest
 */
public class LoginRequest {
    @NotBlank
    private String usernameOrEmail;
    
    @NotBlank
    private String password;

    /**
     * @return the usernameOrEmail
     */
    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param usernameOrEmail the usernameOrEmail to set
     */
    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}