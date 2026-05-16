package com.medschedule.exception;

/**
 * OOP CONCEPT: INHERITANCE
 * Extends RuntimeException — inherits all exception handling behaviour.
 * Custom exception gives meaningful error messages to the client.
 */
public class ResourceNotFoundException extends RuntimeException {

    private final String resourceName;
    private final Long   resourceId;

    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
        this.resourceName = resourceName;
        this.resourceId   = id;
    }

    public String getResourceName() { return resourceName; }
    public Long   getResourceId()   { return resourceId; }
}
