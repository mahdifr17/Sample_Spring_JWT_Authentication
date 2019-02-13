package com.mahdifr.sample_spring_jwt_authentication.model.audit;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * DateAudit
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt"},
    allowGetters = true
)
public abstract class DateAudit implements Serializable {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    
    /**
     * @return the createdAt
     */
    public Instant getCreatedAt() {
        return createdAt;
    }
    /**
     * @return the updatedAt
     */
    public Instant getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param createdAt the createdAt to set
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    /**
     * @param updatedAt the updatedAt to set
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}