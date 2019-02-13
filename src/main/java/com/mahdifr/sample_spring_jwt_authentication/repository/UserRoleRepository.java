package com.mahdifr.sample_spring_jwt_authentication.repository;

import com.mahdifr.sample_spring_jwt_authentication.model.UserRoleModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRoleRepository
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleModel, Long> {
    
}