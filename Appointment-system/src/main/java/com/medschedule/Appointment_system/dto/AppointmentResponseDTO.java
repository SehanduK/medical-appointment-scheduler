package com.medschedule.dto;

import com.medschedule.model.AppointmentStatus;
import com.medschedule.model.AppointmentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * OOP CONCEPT: INFORMATION HIDING
 *
 * We control EXACTLY what data is exposed to the API consumer.
 * Internal fields like Hibernate proxy details, lazy-loaded
 * collections, etc. are hidden behind this clean DTO.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private String patientName;
    private String patientId;
    private String doctorName;
    private String department;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private AppointmentStatus status;
    private AppointmentType appointmentType;
    private String reason;
    private boolean canBeCancelled;   // exposed as derived info
    private boolean todayAppointment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
