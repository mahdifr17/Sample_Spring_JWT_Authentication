package com.mahdifr.sample_spring_jwt_authentication.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mahdifr.sample_spring_jwt_authentication.model.audit.DateAudit;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * UserRoleModel
 */
@Entity
@Table(name = "tmst_user_role")
public class UserRoleModel extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "id_user", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserModel user;
    
    @ManyToOne(fetch = FetchType.EAGER, optional=false)
    @JoinColumn(name = "id_role", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private RoleModel role;

    public UserRoleModel() {}

    /**
     * Constructor
     * @param user
     * @param role
      */
    public UserRoleModel(UserModel user, RoleModel role) {
        this.user = user;
        this.role = role;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @return the user
     */
    public UserModel getUser() {
        return user;
    }
    /**
     * @return the role
     */
    public RoleModel getRole() {
        return role;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @param user the user to set
     */
    public void setUser(UserModel user) {
        this.user = user;
    }
    /**
     * @param role the role to set
     */
    public void setRole(RoleModel role) {
        this.role = role;
    }
}