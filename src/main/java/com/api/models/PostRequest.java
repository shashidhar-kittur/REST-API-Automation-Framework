package com.api.models;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * PostRequest POJO
 * 
 * Represents a Post entity payload for CRUD test scenarios.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostRequest {

    private String title;
    private String body;
    private Integer userId;

    public PostRequest() {}

    public PostRequest(String title, String body, Integer userId) {
        this.title = title;
        this.body = body;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostRequest{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }
}
