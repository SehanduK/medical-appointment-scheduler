package com.medschedule.service;

import com.medschedule.dto.AppointmentRequestDTO;
import com.medschedule.dto.AppointmentResponseDTO;
import com.medschedule.exception.ResourceNotFoundException;
import com.medschedule.model.Appointment;
import com.medschedule.model.AppointmentStatus;
import com.medschedule.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * OOP CONCEPT: POLYMORPHISM + INHERITANCE + ENCAPSULATION
 *
 * - POLYMORPHISM  : Implements IAppointmentService. The controller
 *                   uses the interface type — this class is injected
 *                   at runtime (runtime polymorphism via Spring DI).
 *
 * - ENCAPSULATION : mapToResponse() is private — the DTO conversion
 *                   logic is hidden inside this class. Callers get
 *                   clean DTOs without knowing how they're built.
 *
 * - INFORMATION   : The controller never touches the Appointment entity
 *   HIDING          directly — all access is through DTOs returned here.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements IAppointmentService {

    // ENCAPSULATION: Repository is private — injected, not exposed
    private final AppointmentRepository appointmentRepository;

    // ── CREATE ─────────────────────────────────────────────────────────
    @Override
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {
        Appointment appointment = new Appointment(
            dto.getPatientName(),
            dto.getPatientId(),
            dto.getDoctorName(),
            dto.getDepartment(),
            dto.getAppointmentDate(),
            dto.getAppointmentTime(),
            dto.getAppointmentType(),
            dto.getReason()
        );
        if (dto.getStatus() != null) {
            appointment.setStatus(dto.getStatus());
        }
        Appointment saved = appointmentRepository.save(appointment);
        return mapToResponse(saved);
    }

    // ── READ ───────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll()
            .stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AppointmentResponseDTO getAppointmentById(Long id) {
        Appointment a = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        return mapToResponse(a);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getByStatus(AppointmentStatus status) {
        return appointmentRepository.findByStatus(status)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getByDepartment(String department) {
        return appointmentRepository.findByDepartment(department)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getTodaysAppointments() {
        return appointmentRepository.findByAppointmentDate(LocalDate.now())
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> search(String query) {
        return appointmentRepository.search(query)
            .stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    // ── UPDATE ─────────────────────────────────────────────────────────
    @Override
    public AppointmentResponseDTO updateAppointment(Long id, AppointmentRequestDTO dto) {
        Appointment existing = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));

        // Update fields via setters (ENCAPSULATION — no direct field access)
        existing.setPatientName(dto.getPatientName());
        existing.setPatientId(dto.getPatientId());
        existing.setDoctorName(dto.getDoctorName());
        existing.setDepartment(dto.getDepartment());
        existing.setAppointmentDate(dto.getAppointmentDate());
        existing.setAppointmentTime(dto.getAppointmentTime());
        existing.setAppointmentType(dto.getAppointmentType());
        existing.setReason(dto.getReason());
        if (dto.getStatus() != null) {
            existing.setStatus(dto.getStatus());
        }

        return mapToResponse(appointmentRepository.save(existing));
    }

    @Override
    public AppointmentResponseDTO cancelAppointment(Long id) {
        Appointment a = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        // INFORMATION HIDING: cancel() validates internally — we don't check status here
        a.cancel();
        return mapToResponse(appointmentRepository.save(a));
    }

    @Override
    public AppointmentResponseDTO completeAppointment(Long id) {
        Appointment a = appointmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Appointment", id));
        a.complete();
        return mapToResponse(appointmentRepository.save(a));
    }

    // ── DELETE ─────────────────────────────────────────────────────────
    @Override
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Appointment", id);
        }
        appointmentRepository.deleteById(id);
    }

    // ── STATS ──────────────────────────────────────────────────────────
    @Override
    @Transactional(readOnly = true)
    public Map<String, Long> getDashboardStats() {
        Map<String, Long> stats = new LinkedHashMap<>();
        stats.put("total",     appointmentRepository.count());
        stats.put("scheduled", appointmentRepository.countByStatus(AppointmentStatus.SCHEDULED));
        stats.put("pending",   appointmentRepository.countByStatus(AppointmentStatus.PENDING));
        stats.put("completed", appointmentRepository.countByStatus(AppointmentStatus.COMPLETED));
        stats.put("cancelled", appointmentRepository.countByStatus(AppointmentStatus.CANCELLED));
        return stats;
    }

    // ── PRIVATE HELPER: INFORMATION HIDING ────────────────────────────
    /**
     * Maps entity → DTO. Private — callers never see the entity.
     * This is information hiding: internal model is hidden behind the DTO.
     */
    private AppointmentResponseDTO mapToResponse(Appointment a) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setId(a.getId());
        dto.setPatientName(a.getPatientName());
        dto.setPatientId(a.getPatientId());
        dto.setDoctorName(a.getDoctorName());
        dto.setDepartment(a.getDepartment());
        dto.setAppointmentDate(a.getAppointmentDate());
        dto.setAppointmentTime(a.getAppointmentTime());
        dto.setStatus(a.getStatus());
        dto.setAppointmentType(a.getAppointmentType());
        dto.setReason(a.getReason());
        dto.setCanBeCancelled(a.canBeCancelled());   // derived from entity logic
        dto.setTodayAppointment(a.isTodayAppointment());
        dto.setCreatedAt(a.getCreatedAt());
        dto.setUpdatedAt(a.getUpdatedAt());
        return dto;
    }
}
