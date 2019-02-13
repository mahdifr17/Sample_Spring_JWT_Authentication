package com.mahdifr.sample_spring_jwt_authentication.payload;

import java.time.Instant;

/**
 * UserProfile
 */
public class UserProfile {
    private Long id;
    private String username;
    private String name;
    private Instant joinedAt;
    
    public UserProfile(Long id, String username, String name, Instant joinedAt) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.joinedAt = joinedAt;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the joinedAt
     */
    public Instant getJoinedAt() {
        return joinedAt;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param joinedAt the joinedAt to set
     */
    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }
}