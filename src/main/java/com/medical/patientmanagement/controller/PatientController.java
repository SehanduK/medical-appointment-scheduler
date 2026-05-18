package com.medical.patientmanagement.controller;

import com.medical.patientmanagement.model.Patient;
import com.medical.patientmanagement.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller //Marks this as a web request handler
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    //Gets all patients
    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("patients", service.getAllPatients());
        return "index";
    }

    //Creates empty Patient, shows new_patient.
    @GetMapping("/showNewPatientForm")
    public String showNewPatientForm(Model model) {

        Patient patient = new Patient();

        model.addAttribute("patient", patient);

        return "new_patient";
    }

    //Saves patient to DB
    @PostMapping("/savePatient")
    public String savePatient(@ModelAttribute("patient") Patient patient) {

        service.savePatient(patient);

        return "redirect:/";
    }

    //Finds patient by ID, shows update_patient
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable Long id, Model model) {

        Patient patient = service.getPatientById(id);

        model.addAttribute("patient", patient);

        return "update_patient";
    }

    //Deletes patient by ID
    @GetMapping("/deletePatient/{id}")
    public String deletePatient(@PathVariable Long id) {

        service.deletePatient(id);

        return "redirect:/";
    }
}
