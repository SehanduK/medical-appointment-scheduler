package com.patient.patientscheduling.controller;

import com.patient.patientscheduling.model.Patient;
import com.patient.patientscheduling.model.Prescription;
import com.patient.patientscheduling.repository.PatientRepository;
import com.patient.patientscheduling.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/add")
    public String showAddPage(Model model) {
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("patients", patientRepository.findAll());
        return "prescriptions";
    }

    @PostMapping("/save")
    public String savePrescription(
            @ModelAttribute("prescription") Prescription prescription,
            @RequestParam("patientId") Long patientId,
            Model model) {

        Patient patient = patientRepository.findById(patientId).orElse(null);

        if (patient == null ||
                prescription.getMedication() == null || prescription.getMedication().trim().isEmpty() ||
                prescription.getDosage() == null || prescription.getDosage().trim().isEmpty()) {

            model.addAttribute("error", "Patient, Medication, and Dosage are all required.");
            model.addAttribute("patients", patientRepository.findAll());
            return "prescriptions";
        }

        // Duplicate check
        if (prescriptionRepository.existsByPatientAndMedication(patient, prescription.getMedication().trim())) {
            model.addAttribute("error", "This prescription already exists for that patient and medication.");
            model.addAttribute("patients", patientRepository.findAll());
            return "prescriptions";
        }

        prescription.setPatient(patient);
        prescriptionRepository.save(prescription);
        return "redirect:/prescription/add?success";
    }

    @GetMapping("/all")
    public String showAllPrescriptions(Model model) {
        List<Prescription> list = prescriptionRepository.findAll();
        model.addAttribute("allPrescriptions", list);
        return "edit-prescription";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid prescription ID: " + id));
        model.addAttribute("prescription", prescription);
        model.addAttribute("patients", patientRepository.findAll());
        return "update-prescription";
    }

    @PostMapping("/update/{id}")
    public String updatePrescription(
            @PathVariable Long id,
            @ModelAttribute("prescription") Prescription prescription,
            @RequestParam("patientId") Long patientId) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid patient ID"));

        prescription.setId(id);
        prescription.setPatient(patient);
        prescriptionRepository.save(prescription);
        return "redirect:/prescription/all?updated";
    }

    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionRepository.deleteById(id);
        return "redirect:/prescription/all?deleted";
    }
}
