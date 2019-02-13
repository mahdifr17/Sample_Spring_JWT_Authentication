package com.mahdifr.sample_spring_jwt_authentication.repository;

import java.util.Optional;

import com.mahdifr.sample_spring_jwt_authentication.constant.RoleName;
import com.mahdifr.sample_spring_jwt_authentication.model.RoleModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * RoleRepository
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByName(RoleName name);
}