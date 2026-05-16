package com.medschedule.dto;

import com.medschedule.model.AppointmentStatus;
import com.medschedule.model.AppointmentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * OOP CONCEPT: INFORMATION HIDING + ENCAPSULATION
 *
 * DTO (Data Transfer Object) hides the internal Appointment entity
 * from the outside world. The API receives/sends DTOs only —
 * the real entity structure is hidden from callers.
 *
 * ENCAPSULATION: All fields are private with controlled access.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    @NotBlank(message = "Patient name is required")
    private String patientName;

    @NotBlank(message = "Patient ID is required")
    private String patientId;

    @NotBlank(message = "Doctor name is required")
    private String doctorName;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "Appointment date is required")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment time is required")
    private LocalTime appointmentTime;

    private AppointmentStatus status = AppointmentStatus.SCHEDULED;

    private AppointmentType appointmentType = AppointmentType.CONSULTATION;

    private String reason;
}
