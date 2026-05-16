package com.medschedule.repository;

import com.medschedule.model.Appointment;
import com.medschedule.model.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * OOP CONCEPT: ABSTRACTION + INHERITANCE
 *
 * - ABSTRACTION : Repository is an interface (abstract contract).
 *                 Spring provides the concrete implementation at runtime.
 * - INHERITANCE : JpaRepository already provides findAll(), findById(),
 *                 save(), deleteById() — we inherit them for free.
 *
 * We only define the EXTRA queries our system needs.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Filter by status
    List<Appointment> findByStatus(AppointmentStatus status);

    // Filter by department
    List<Appointment> findByDepartment(String department);

    // Filter by doctor
    List<Appointment> findByDoctorName(String doctorName);

    // Appointments on a specific date
    List<Appointment> findByAppointmentDate(LocalDate date);

    // Today's appointments
    List<Appointment> findByAppointmentDateAndStatus(LocalDate date, AppointmentStatus status);

    // Search across multiple fields
    @Query("SELECT a FROM Appointment a WHERE " +
           "LOWER(a.patientName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(a.doctorName)  LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(a.department)  LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(a.reason)      LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Appointment> search(@Param("q") String query);

    // Count by status (for dashboard stats)
    long countByStatus(AppointmentStatus status);
}
