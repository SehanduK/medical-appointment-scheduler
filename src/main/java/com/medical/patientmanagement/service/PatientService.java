package com.medical.patientmanagement.service;

import com.medical.patientmanagement.model.Patient;
import com.medical.patientmanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public void savePatient(Patient patient) {
        repository.save(patient);
    }

    public Patient getPatientById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletePatient(Long id) {
        repository.deleteById(id);
    }
}
