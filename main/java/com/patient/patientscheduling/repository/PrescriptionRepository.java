package com.patient.patientscheduling.repository;

import com.patient.patientscheduling.model.Patient;
import com.patient.patientscheduling.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    boolean existsByPatientAndMedication(Patient patient, String medication);

    List<Prescription> findByPatient(Patient patient);
}
