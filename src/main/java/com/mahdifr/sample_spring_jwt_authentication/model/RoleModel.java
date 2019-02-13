package com.mahdifr.sample_spring_jwt_authentication.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mahdifr.sample_spring_jwt_authentication.constant.RoleName;
import com.mahdifr.sample_spring_jwt_authentication.model.audit.DateAudit;

import org.hibernate.annotations.NaturalId;

/**
 * RoleModel
 */
@Entity
@Table(name = "tref_role")
public class RoleModel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
	@Column(name = "name", nullable = false, length = 50)
    private RoleName name;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="role")
    @JsonIgnore
    private Set<UserRoleModel> userRole = new HashSet<>();

    public RoleModel() {}
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @return the name
     */
    public RoleName getName() {
        return name;
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
    public void setName(RoleName name) {
        this.name = name;
    }
    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(Set<UserRoleModel> userRole) {
        this.userRole = userRole;
    }
}