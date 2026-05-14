package com.patient.patientscheduling.controller;

import com.patient.patientscheduling.model.Doctor;
import com.patient.patientscheduling.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin") // This prefix applies to EVERYTHING below
public class AdminController {

    @Autowired
    private DoctorRepository doctorRepository;

    // Access via: GET /admin/add-doctor
    @GetMapping("/add-doctor")
    public String showAdminPage(Model model) {
        if (!model.containsAttribute("doctor")) {
            model.addAttribute("doctor", new Doctor());
        }
        return "admin";
    }

    // Access via: POST /admin/save-doctor
    // REMOVED the extra "/admin" from the mapping below
    @PostMapping("/save-doctor")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor, RedirectAttributes redirectAttributes) {

        // 1. Mandatory Field Check
        if (doctor.getName() == null || doctor.getName().trim().isEmpty() ||
                doctor.getSpecialization() == null || doctor.getSpecialization().trim().isEmpty()) {
            return "redirect:/admin/add-doctor?error=missing_fields";
        }

        // 2. Duplicate Check
        boolean exists = doctorRepository.existsByNameAndSpecialization(
                doctor.getName(),
                doctor.getSpecialization()
        );

        if (exists) {
            // 3. Notify Admin of duplicate
            return "redirect:/admin/add-doctor?error=duplicate";
        }

        // 4. Save and Success
        doctorRepository.save(doctor);
        return "redirect:/admin/add-doctor?success=true";
    }

    // Access via: GET /admin/all-doctors
    @GetMapping("/all-doctors")
    public String showAllDoctorsPage(Model model) {
        List<Doctor> list = doctorRepository.findAll();
        model.addAttribute("allDoctors", list);
        return "edit-doctor";
    }

    // Access via: GET /admin/delete/{id}
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/admin/all-doctors?deleted";
    }
}