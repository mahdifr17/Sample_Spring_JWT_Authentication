package com.mahdifr.sample_spring_jwt_authentication.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.auth0.jwt.exceptions.JWTDecodeException;
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
import com.mahdifr.sample_spring_jwt_authentication.security.registration.OnRegistrationCompleteEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping(value="/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // Call CustomUserDetailsService loadUserByUsername(). Return unauthorized error if user is disabled (automatic check UserPrincipal isEnabled())
        Authentication authentication = authenticationManager.authenticate(
            // UsernamePasswordAuthenticationToken in this context will make isAuthenticated() = false. (Untrusted)
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
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest, HttpServletRequest req) {
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

        String appUrl = req.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(appUrl, req.getLocale(), user));

        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping(value="/signup/confirm")
    public ResponseEntity<?> confirmRegisterUser(@RequestParam(name = "token") String token) {
        try {
            if (StringUtils.hasText(token) && tokenProvider.validateVerificationToken(token)) {
                Long userId = tokenProvider.getUserIdFromJWT(token);
    
                UserModel user = userRepository.findById(userId).orElseThrow(
                    () -> new UsernameNotFoundException("User not found")
                );
                
                user.setEnabled(true);
                userRepository.flush();
                return ResponseEntity.ok(new ApiResponse(true, "User confirmation successfully"));
            }
        } catch (JWTDecodeException e) {
            logger.error("Invalid Token", e);
        }
        return new ResponseEntity<>(new ApiResponse(false, "Invalid Token!"), HttpStatus.BAD_REQUEST);
    }
}