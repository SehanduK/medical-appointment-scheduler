package com.medschedule.model;

/**
 * OOP CONCEPT: ABSTRACTION
 * Abstracts appointment status as a type-safe enum
 * instead of raw strings — prevents invalid status values.
 */
public enum AppointmentStatus {
    SCHEDULED,
    PENDING,
    COMPLETED,
    CANCELLED;

    public boolean isActive() {
        return this == SCHEDULED || this == PENDING;
    }

    public boolean isFinal() {
        return this == COMPLETED || this == CANCELLED;
    }
}
