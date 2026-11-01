package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * RegisterResponse POJO
 * 
 * Represents response from registration/login endpoints.
 * In positive scenarios, returns 'id' and 'token'.
 * In negative scenarios (e.g. 400 Bad Request), returns 'error'.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse {

    private Integer id;
    private String token;
    private String error;

    public RegisterResponse() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
