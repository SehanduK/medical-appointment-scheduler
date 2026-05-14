package com.patient.patientscheduling.controller;

import com.patient.patientscheduling.model.Prescription;
import com.patient.patientscheduling.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    // ── CREATE ── GET /prescription/add
    @GetMapping("/add")
    public String showAddPage(Model model) {
        if (!model.containsAttribute("prescription")) {
            model.addAttribute("prescription", new Prescription());
        }
        return "prescriptions";
    }

    // ── CREATE ── POST /prescription/save
    @PostMapping("/save")
    public String savePrescription(@ModelAttribute("prescription") Prescription prescription,
                                   RedirectAttributes redirectAttributes) {

        // Mandatory field check
        if (prescription.getPatientName() == null || prescription.getPatientName().trim().isEmpty() ||
                prescription.getDoctorName() == null || prescription.getDoctorName().trim().isEmpty() ||
                prescription.getMedication() == null || prescription.getMedication().trim().isEmpty() ||
                prescription.getDosage() == null || prescription.getDosage().trim().isEmpty()) {
            return "redirect:/prescription/add?error=missing_fields";
        }

        // Duplicate check
        boolean exists = prescriptionRepository.existsByPatientNameAndMedication(
                prescription.getPatientName(),
                prescription.getMedication()
        );

        if (exists) {
            return "redirect:/prescription/add?error=duplicate";
        }

        prescriptionRepository.save(prescription);
        return "redirect:/prescription/add?success=true";
    }

    // ── READ ── GET /prescription/all
    @GetMapping("/all")
    public String showAllPrescriptions(Model model) {
        List<Prescription> list = prescriptionRepository.findAll();
        model.addAttribute("allPrescriptions", list);
        return "edit-prescription";
    }

    // ── UPDATE ── GET /prescription/edit/{id}
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid prescription ID: " + id));
        model.addAttribute("prescription", prescription);
        return "update-prescription";
    }

    // ── UPDATE ── POST /prescription/update/{id}
    @PostMapping("/update/{id}")
    public String updatePrescription(@PathVariable("id") Long id,
                                     @ModelAttribute("prescription") Prescription prescription) {
        prescription.setId(id);
        prescriptionRepository.save(prescription);
        return "redirect:/prescription/all?updated";
    }

    // ── DELETE ── GET /prescription/delete/{id}
    @GetMapping("/delete/{id}")
    public String deletePrescription(@PathVariable("id") Long id) {
        prescriptionRepository.deleteById(id);
        return "redirect:/prescription/all?deleted";
    }
}
