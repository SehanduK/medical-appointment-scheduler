package com.medical.patientmanagement.controller;

import com.medical.patientmanagement.model.Patient;
import com.medical.patientmanagement.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("patients", service.getAllPatients());
        return "index";
    }

    @GetMapping("/showNewPatientForm")
    public String showNewPatientForm(Model model) {

        Patient patient = new Patient();

        model.addAttribute("patient", patient);

        return "new_patient";
    }

    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {

        service.savePatient(patient);

        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable Long id, Model model) {

        Patient patient = service.getPatientById(id);

        model.addAttribute("patient", patient);

        return "update_patient";
    }

    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable Long id) {

        service.deletePatient(id);

        return "redirect:/";
    }
}
