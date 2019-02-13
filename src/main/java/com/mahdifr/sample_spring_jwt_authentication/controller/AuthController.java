package com.mahdifr.sample_spring_jwt_authentication.controller;

import java.net.URI;

import javax.validation.Valid;

import com.mahdifr.sample_spring_jwt_authentication.constant.RoleName;
import com.mahdifr.sample_spring_jwt_authentication.exception.AppException;
import com.mahdifr.sample_spring_jwt_authentication.model.RoleModel;
import com.mahdifr.sample_spring_jwt_authentication.model.UserModel;
import com.mahdifr.sample_spring_jwt_authentication.model.UserRoleModel;
import com.mahdifr.sample_spring_jwt_authentication.payload.ApiResponse;
import com.mahdifr.sample_spring_jwt_authentication.payload.JwtAuthenticationResponse;
import com.mahdifr.sample_spring_jwt_authentication.payload.LoginRequest;
import com.mahdifr.sample_spring_jwt_authentication.payload.SignUpRequest;
import com.mahdifr.sample_spring_jwt_authentication.repository.RoleRepository;
import com.mahdifr.sample_spring_jwt_authentication.repository.UserRepository;
import com.mahdifr.sample_spring_jwt_authentication.repository.UserRoleRepository;
import com.mahdifr.sample_spring_jwt_authentication.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping(value="/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
            // UsernamePasswordAuthenticationToken in this context will make isAuthenticated = false
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsernameOrEmail(), 
                loginRequest.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping(value="/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        
        UserModel user = new UserModel(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        RoleModel role = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(
            () -> new AppException("User Role not set.")
        );

        userRepository.save(user);

        UserRoleModel userRole = new UserRoleModel(user, role);
        userRoleRepository.save(userRole);

        URI location = ServletUriComponentsBuilder
            .fromCurrentContextPath().path("/api/users/{username}")
            .buildAndExpand(user.getUsername()).toUri();
        
        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }
}