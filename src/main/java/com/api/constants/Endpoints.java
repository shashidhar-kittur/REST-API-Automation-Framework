package com.api.constants;

/**
 * Endpoints
 * 
 * Centralized repository of all API route paths used across the framework.
 * Keeping endpoints here prevents hardcoding URLs across test classes,
 * making maintenance simple when endpoints change.
 */
public final class Endpoints {

    // Private constructor prevents instantiation of this utility class
    private Endpoints() {}

    // User Management Endpoints
    public static final String USERS = "/users";
    public static final String USER_BY_ID = "/users/{id}";

    // Post Management Endpoints
    public static final String POSTS = "/posts";
    public static final String POST_BY_ID = "/posts/{id}";

    // Non-existent Route (for Negative 404 Testing)
    public static final String INVALID_ENDPOINT = "/non_existent_resource";
}
