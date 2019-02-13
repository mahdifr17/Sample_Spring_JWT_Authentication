package com.mahdifr.sample_spring_jwt_authentication.payload;

/**
 * JwtAuthenticationResponse
 */
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    
    /**
     * Constructor
     * @param accessToken
      */
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }
    /**
     * @return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    /**
     * @param tokenType the tokenType to set
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}