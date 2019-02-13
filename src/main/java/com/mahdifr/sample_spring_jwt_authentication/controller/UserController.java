package com.mahdifr.sample_spring_jwt_authentication.controller;

import com.mahdifr.sample_spring_jwt_authentication.exception.ResourceNotFoundException;
import com.mahdifr.sample_spring_jwt_authentication.model.UserModel;
import com.mahdifr.sample_spring_jwt_authentication.payload.UserProfile;
import com.mahdifr.sample_spring_jwt_authentication.payload.UserSummary;
import com.mahdifr.sample_spring_jwt_authentication.repository.UserRepository;
import com.mahdifr.sample_spring_jwt_authentication.security.CurrentUser;
import com.mahdifr.sample_spring_jwt_authentication.security.UserPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



/**
 * UserController
 */
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping(value="/user/me")
    @PreAuthorize(value="hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }
    
    @GetMapping(value="/user/checkAvailability/username")
    public Boolean checkUsernameAvailability(@RequestParam(name = "username") String username) {
        return !userRepository.existsByUsername(username);
    }

    @GetMapping(value="/user/checkAvailability/email")
    public Boolean checkEmailAvailability(@RequestParam(name = "email") String email) {
        return !userRepository.existsByEmail(email);
    }

    @GetMapping(value="/user/{username}")
    @PreAuthorize(value="hasRole('USER')")
    public UserProfile getUserProfile(@PathVariable(value="username") String username) {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(
            () -> new ResourceNotFoundException("User", "username", username)
        );

        UserProfile userProfile = new UserProfile(userModel.getId(), userModel.getUsername(), userModel.getName(), userModel.getCreatedAt());
        return userProfile;
    }
}