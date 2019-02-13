package com.mahdifr.sample_spring_jwt_authentication.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * JwtTokenProvider
 */
@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtKey}")
    private String jwtKey;
    
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;
    
    /**
     * Method to generate token based on user id
     * @param auth
     * @return
      */
    public String generateToken(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return JWT.create()
            .withSubject(Long.toString(userPrincipal.getId()))
            .withIssuedAt(now)
            .withExpiresAt(expiryDate)
            .sign(Algorithm.HMAC512(jwtKey));
    }

    /**
     * Method to get user id from token
     * @param token
     * @return
      */
    public Long getUserIdFromJWT(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return Long.parseLong(jwt.getSubject());
    }
    
    /**
     * Method to validate token algorithm, expire time, and claim value
     * @param token
     * @return
      */
    public boolean validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(jwtKey)).build();
            verifier.verify(token);
            return true;
        } catch (AlgorithmMismatchException e) {
            logger.error("Invalid JWT algorithm");
        } catch (TokenExpiredException e) {
            logger.error("Invalid JWT token");
        } catch (InvalidClaimException e) {
            logger.error("JWT claims string empty");
        }
        return false;
    }
}