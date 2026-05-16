package com.medschedule.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * OOP CONCEPT: INHERITANCE + INFORMATION HIDING + ABSTRACTION
 *
 * - ABSTRACTION    : Abstract class cannot be instantiated directly.
 *                    Forces subclasses to be concrete entities.
 * - INHERITANCE    : Appointment extends this class and inherits
 *                    id, createdAt, updatedAt automatically.
 * - INFORMATION    : Fields are private. Subclasses access them
 *   HIDING           only through inherited protected/public getters.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    // INFORMATION HIDING: private — not directly accessible outside
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Auto-set timestamps before persisting
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
