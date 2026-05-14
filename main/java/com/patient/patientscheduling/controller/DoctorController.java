package com.patient.patientscheduling.controller;

import com.patient.patientscheduling.model.Doctor;
import com.patient.patientscheduling.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DoctorController {

    @Autowired
    private DoctorRepository doctorRepository;

    @PostMapping("/select-specialty")
    public String getDoctors(@RequestParam("specialty") String specialty, Model model) {
        // 1. Fetch from DB
        List<Doctor> foundDoctors = doctorRepository.findBySpecialization(specialty);

        // 2. LOG TO CONSOLE (Check your IDE terminal!)
        System.out.println("Searching for: " + specialty);
        System.out.println("Doctors found: " + foundDoctors.size());

        // 3. YOU MUST ADD TO MODEL
        model.addAttribute("doctors", foundDoctors);
        model.addAttribute("selectedSpecialty", specialty);

        return "channeling";
    }
}