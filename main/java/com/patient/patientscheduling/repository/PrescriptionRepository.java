package com.patient.patientscheduling.repository;

import com.patient.patientscheduling.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    boolean existsByPatientNameAndMedication(String patientName, String medication);
    List<Prescription> findByPatientName(String patientName);
    List<Prescription> findByDoctorName(String doctorName);
}
