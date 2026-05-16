package com.medschedule.controller;

import com.medschedule.dto.ApiResponse;
import com.medschedule.dto.AppointmentRequestDTO;
import com.medschedule.dto.AppointmentResponseDTO;
import com.medschedule.model.AppointmentStatus;
import com.medschedule.service.IAppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * OOP CONCEPT: ABSTRACTION + POLYMORPHISM
 *
 * - ABSTRACTION  : Controller depends on IAppointmentService (interface),
 *                  NOT on AppointmentServiceImpl (concrete class).
 *                  → Depends on abstraction, not implementation.
 *
 * - POLYMORPHISM : Spring injects the correct implementation at runtime.
 *                  If we swap the impl class, this controller is unchanged.
 *
 * REST API:
 *   POST   /api/appointments            → Create
 *   GET    /api/appointments            → Get all
 *   GET    /api/appointments/{id}       → Get by ID
 *   GET    /api/appointments/search     → Search
 *   GET    /api/appointments/status/{s} → Filter by status
 *   GET    /api/appointments/dept/{d}   → Filter by department
 *   GET    /api/appointments/today      → Today's appointments
 *   GET    /api/appointments/stats      → Dashboard stats
 *   PUT    /api/appointments/{id}       → Full update
 *   PATCH  /api/appointments/{id}/cancel   → Cancel
 *   PATCH  /api/appointments/{id}/complete → Complete
 *   DELETE /api/appointments/{id}       → Delete
 */
@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")   // allow frontend to connect
public class AppointmentController {

    // Depends on interface — ABSTRACTION
    private final IAppointmentService appointmentService;

    // ── CREATE ─────────────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> create(
            @Valid @RequestBody AppointmentRequestDTO dto) {

        AppointmentResponseDTO created = appointmentService.createAppointment(dto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.success("Appointment created successfully", created));
    }

    // ── READ ALL ───────────────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getAll() {
        List<AppointmentResponseDTO> list = appointmentService.getAllAppointments();
        return ResponseEntity.ok(ApiResponse.success("Appointments fetched", list));
    }

    // ── READ BY ID ─────────────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> getById(@PathVariable Long id) {
        AppointmentResponseDTO dto = appointmentService.getAppointmentById(id);
        return ResponseEntity.ok(ApiResponse.success("Appointment found", dto));
    }

    // ── SEARCH ────────────────────────────────────────────────────────
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> search(
            @RequestParam String q) {
        return ResponseEntity.ok(
            ApiResponse.success("Search results", appointmentService.search(q)));
    }

    // ── FILTER BY STATUS ──────────────────────────────────────────────
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getByStatus(
            @PathVariable AppointmentStatus status) {
        return ResponseEntity.ok(
            ApiResponse.success("Filtered by status", appointmentService.getByStatus(status)));
    }

    // ── FILTER BY DEPARTMENT ──────────────────────────────────────────
    @GetMapping("/dept/{department}")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getByDept(
            @PathVariable String department) {
        return ResponseEntity.ok(
            ApiResponse.success("Filtered by department", appointmentService.getByDepartment(department)));
    }

    // ── TODAY'S APPOINTMENTS ──────────────────────────────────────────
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<AppointmentResponseDTO>>> getToday() {
        return ResponseEntity.ok(
            ApiResponse.success("Today's appointments", appointmentService.getTodaysAppointments()));
    }

    // ── DASHBOARD STATS ───────────────────────────────────────────────
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getStats() {
        return ResponseEntity.ok(
            ApiResponse.success("Dashboard stats", appointmentService.getDashboardStats()));
    }

    // ── FULL UPDATE ───────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentRequestDTO dto) {

        AppointmentResponseDTO updated = appointmentService.updateAppointment(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Appointment updated", updated));
    }

    // ── CANCEL ────────────────────────────────────────────────────────
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponse.success("Appointment cancelled", appointmentService.cancelAppointment(id)));
    }

    // ── COMPLETE ──────────────────────────────────────────────────────
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<AppointmentResponseDTO>> complete(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponse.success("Appointment completed", appointmentService.completeAppointment(id)));
    }

    // ── DELETE ────────────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.ok(ApiResponse.success("Appointment deleted successfully"));
    }
}
