package com.medical.MedicalAppointmentSystem.repository;

import com.medical.MedicalAppointmentSystem.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {
}