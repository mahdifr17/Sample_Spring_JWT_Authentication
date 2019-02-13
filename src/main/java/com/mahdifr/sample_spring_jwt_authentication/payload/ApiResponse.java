package com.mahdifr.sample_spring_jwt_authentication.payload;

/**
 * ApiResponse
 */
public class ApiResponse {
    private Boolean success;
    private String message;

    /**
     * Constructor
     * @param success
     * @param message
      */
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    /**
     * @return the success
     */
    public Boolean getSuccess() {
        return success;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}