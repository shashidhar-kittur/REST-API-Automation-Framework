package com.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * UserRequest POJO
 * 
 * Represents the request payload for creating or updating a user.
 * Jackson automatically serializes this Java object to JSON.
 * NON_NULL ensures that fields not set (null) won't be sent in the request body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRequest {

    private String name;
    private String username;
    private String email;

    // Default constructor needed for Jackson
    public UserRequest() {}

    // Parameterized constructor for easy object creation
    public UserRequest(String name, String username, String email) {
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
