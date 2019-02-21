package com.mahdifr.sample_spring_jwt_authentication.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahdifr.sample_spring_jwt_authentication.model.audit.DateAudit;

/**
 * UserModel
 */
@Entity
@Table(name = "tmst_user")
public class UserModel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
	@Size(max = 40)
	@Column(name = "name", nullable = false)
	private String name;

    @NotBlank
	@Size(max = 15)
	@Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @NotBlank
	@Size(max = 40)
    @Column(name = "email", nullable = false, unique = true)
    @Email
	private String email;
	
	@NotBlank
	@Size(max = 100)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled", nullable = false)
    private Boolean enabled = false;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="user")
    @JsonIgnore
    private Set<UserRoleModel> userRole = new HashSet<>();
    
    public UserModel() {}

    /**
     * Constructor
     * @param name
     * @param username
     * @param email
     * @param password
      */
    public UserModel(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = false;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }
    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }
    /**
     * @return the userRole
     */
    public Set<UserRoleModel> getUserRole() {
        return userRole;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(Set<UserRoleModel> userRole) {
        this.userRole = userRole;
    }
}