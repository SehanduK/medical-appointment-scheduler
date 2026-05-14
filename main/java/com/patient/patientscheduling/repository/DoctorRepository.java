package com.patient.patientscheduling.repository;

import com.patient.patientscheduling.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByNameAndSpecialization(String name, String specialization);

    List<Doctor> findBySpecialization(String specialty);
}