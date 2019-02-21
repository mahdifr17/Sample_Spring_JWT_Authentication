package com.mahdifr.sample_spring_jwt_authentication.security;

import com.mahdifr.sample_spring_jwt_authentication.model.UserModel;
import com.mahdifr.sample_spring_jwt_authentication.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * CustomUserDetailsService
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    /** 
     * Method loaded when user attempt login
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(
            () -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );

        return UserPrincipal.create(user);
    }

    /**
     * Method used by jwt to validate user id inside token.
     * Called everytime user accessing endpoint
     * @param id
     * @return
      */
    @Transactional
    public UserDetails loadUserById(Long id) {
        UserModel user = userRepository.findByIdAndEnabledTrue(id).orElseThrow(
            () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return UserPrincipal.create(user);
    }
}