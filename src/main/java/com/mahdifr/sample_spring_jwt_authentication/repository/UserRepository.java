package com.mahdifr.sample_spring_jwt_authentication.repository;

import java.util.Optional;

import com.mahdifr.sample_spring_jwt_authentication.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository
 */
@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUsernameOrEmailAndEnabledTrue(String username, String email);
    Optional<UserModel> findByUsernameOrEmail(String username, String email);
    Optional<UserModel> findByIdAndEnabledTrue(Long id);
    Optional<UserModel> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}