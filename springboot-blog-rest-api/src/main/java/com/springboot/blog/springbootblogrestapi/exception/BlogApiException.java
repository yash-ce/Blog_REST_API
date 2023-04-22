package com.springboot.blog.springbootblogrestapi.exception;

import org.springframework.http.HttpStatus;

public class BlogApiException extends RuntimeException{

    private HttpStatus status;
    private String message;
    
    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public BlogApiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
    public BlogApiException(String arg0, HttpStatus status, String message) {
        super(arg0);
        this.status = status;
        this.message = message;
    }
    
}
