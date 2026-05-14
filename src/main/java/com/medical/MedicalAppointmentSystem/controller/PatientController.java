package com.medical.MedicalAppointmentSystem.controller;

import com.medical.MedicalAppointmentSystem.model.Patient;
import com.medical.MedicalAppointmentSystem.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    PatientService patientService;

    // READ - Show all patients
    @GetMapping
    public String listPatients(Model model) {
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    // CREATE - Add new patient
    @PostMapping("/add")
    public String addPatient(@RequestParam String id,
                             @RequestParam String name,
                             @RequestParam String phone,
                             @RequestParam int age,
                             Model model) {
        // Phone validation
        if (!phone.matches("[0-9]{10}")) {
            model.addAttribute("error", "Phone number must be exactly 10 digits!");
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients";
        }
        // Age validation
        if (age < 1 || age > 99) {
            model.addAttribute("error", "Age must be between 1 and 120!");
            model.addAttribute("patients", patientService.getAllPatients());
            return "patients";
        }
        patientService.addPatient(new Patient(id, name, phone, age));
        return "redirect:/patients";
    }

    // UPDATE - Show update form
    @GetMapping("/edit/{id}")
    public String editPatient(@PathVariable String id, Model model) {
        model.addAttribute("patient", patientService.getPatientById(id));
        model.addAttribute("patients", patientService.getAllPatients());
        return "patients";
    }

    // UPDATE - Save updated patient
    @PostMapping("/update")
    public String updatePatient(@RequestParam String id,
                                @RequestParam String name,
                                @RequestParam String phone,
                                @RequestParam int age) {
        patientService.updatePatient(new Patient(id, name, phone, age));
        return "redirect:/patients";
    }

    // DELETE
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}
