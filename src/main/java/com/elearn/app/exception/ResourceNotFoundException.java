package com.elearn.app.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException() {
        super("Resource not found !!");
    }

    public ResourceNotFoundException(String courseNotFound) {
        super(courseNotFound);
    }
}
