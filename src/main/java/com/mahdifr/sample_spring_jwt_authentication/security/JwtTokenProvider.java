package com.mahdifr.sample_spring_jwt_authentication.security;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTDecodeException;
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

    @Value("${app.jwtKeyExpirationInMs}")
    private int jwtExpirationKeyInMs;

    @Value("${app.jwtVerificationKey}")
    private String jwtVerificationKey;

    @Value("${app.jwtVerificationKeyExpirationInMs}")
    private int jwtVerificationKeyExpirationInMs;

    /**
     * Method to generate authentication token
     * @param auth
     * @return
     */
    public String generateToken(Authentication auth) {
        UserPrincipal userPrincipal = (UserPrincipal) auth.getPrincipal();
        return helperGenerateToken(userPrincipal.getId(), jwtKey, jwtExpirationKeyInMs);
    }

    /**
     * Method to generate verification Token
     * @param userId
     * @return
     */
    public String generateVerificationToken(Long userId) {
        return helperGenerateToken(userId, jwtVerificationKey, jwtVerificationKeyExpirationInMs);
    }

    /**
     * Helper to generate Token from user id, key, & expire time
     */
    public String helperGenerateToken(Long userId, String key, int expTime) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expTime);
        return JWT.create().withSubject(Long.toString(userId)).withIssuedAt(now)
                .withExpiresAt(expiryDate).sign(Algorithm.HMAC512(key));
    }

    /**
     * Method to get user id from token
     * @param token
     * @return
     */
    public Long getUserIdFromJWT(String token) throws JWTDecodeException {
        DecodedJWT jwt = JWT.decode(token);
        return Long.parseLong(jwt.getSubject());
    }

    /**
     * Method to validate token
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        return helperValidateToken(token, jwtKey);
    }

    /**
     * Method to validate verification token
     * @param token
     * @return
     */
    public boolean validateVerificationToken(String token) {
        return helperValidateToken(token, jwtVerificationKey);
    }

    /**
     * Helper to validate Token algorithm, expire time, and claim value
     */
    public boolean helperValidateToken(String token, String key) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(key)).build();
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