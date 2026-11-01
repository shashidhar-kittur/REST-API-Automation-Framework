package com.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * PostResponse POJO
 * 
 * Maps response received from /posts endpoints.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PostResponse {

    private Integer id;
    private String title;
    private String body;
    private Integer userId;

    public PostResponse() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        return "PostResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", userId=" + userId +
                '}';
    }
}
