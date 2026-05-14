package com.medical.MedicalAppointmentSystem.service;

import com.medical.MedicalAppointmentSystem.model.Patient;
import com.medical.MedicalAppointmentSystem.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    // READ ALL
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // CREATE
    public void addPatient(Patient patient) {
        patientRepository.save(patient);
    }

    // UPDATE
    public void updatePatient(Patient patient) {
        patientRepository.save(patient);
    }

    // DELETE
    public void deletePatient(String id) {
        patientRepository.deleteById(id);
    }

    // FIND BY ID
    public Patient getPatientById(String id) {
        return patientRepository.findById(id).orElse(null);
    }
}
