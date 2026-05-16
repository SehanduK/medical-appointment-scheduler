package com.medschedule.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * OOP CONCEPT: INHERITANCE
 * Appointment extends BaseEntity — inherits id, createdAt, updatedAt.
 *
 * OOP CONCEPT: ENCAPSULATION
 * All fields are private. External code interacts only through
 * getters/setters (via Lombok) — internal state is protected.
 *
 * OOP CONCEPT: INFORMATION HIDING
 * Business logic (e.g. canBeCancelled, isTodayAppointment) is
 * hidden inside the class. Callers use the method, not raw fields.
 */
@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Appointment extends BaseEntity {

    // ── ENCAPSULATION: All fields private ─────────────────────────────

    @NotBlank(message = "Patient name is required")
    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @NotBlank(message = "Patient ID is required")
    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @NotBlank(message = "Doctor name is required")
    @Column(name = "doctor_name", nullable = false)
    private String doctorName;

    @NotBlank(message = "Department is required")
    @Column(name = "department", nullable = false)
    private String department;

    @NotNull(message = "Appointment date is required")
    @Column(name = "appointment_date", nullable = false)
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    @Column(name = "appointment_time", nullable = false)
    private LocalTime appointmentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_type", nullable = false)
    private AppointmentType appointmentType = AppointmentType.CONSULTATION;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;

    // ── INFORMATION HIDING: Business logic inside the class ───────────

    /**
     * Hides the cancellation-eligibility rule.
     * Callers just ask "canBeCancelled?" — they don't know the rule.
     */
    public boolean canBeCancelled() {
        return this.status == AppointmentStatus.SCHEDULED
            || this.status == AppointmentStatus.PENDING;
    }

    /**
     * Hides what "today's appointment" means internally.
     */
    public boolean isTodayAppointment() {
        return LocalDate.now().equals(this.appointmentDate);
    }

    /**
     * Hides status transition logic.
     * The SERVICE calls this; it doesn't manipulate status directly.
     */
    public void cancel() {
        if (!canBeCancelled()) {
            throw new IllegalStateException(
                "Cannot cancel appointment with status: " + this.status
            );
        }
        this.status = AppointmentStatus.CANCELLED;
    }

    public void complete() {
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled appointment.");
        }
        this.status = AppointmentStatus.COMPLETED;
    }

    // ── Constructor for easy object creation ──────────────────────────
    public Appointment(String patientName, String patientId, String doctorName,
                       String department, LocalDate appointmentDate,
                       LocalTime appointmentTime, AppointmentType appointmentType,
                       String reason) {
        this.patientName     = patientName;
        this.patientId       = patientId;
        this.doctorName      = doctorName;
        this.department      = department;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentType = appointmentType;
        this.reason          = reason;
        this.status          = AppointmentStatus.SCHEDULED;
    }
}
