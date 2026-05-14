package com.patient.patientscheduling.controller;

import com.patient.patientscheduling.model.Doctor;
import com.patient.patientscheduling.model.Prescription;
import com.patient.patientscheduling.repository.DoctorRepository;
import com.patient.patientscheduling.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin";
    }

    @GetMapping("/add-doctor")
    public String showAddDoctorPage(Model model) {
        if (!model.containsAttribute("doctor")) {
            model.addAttribute("doctor", new Doctor());
        }
        return "add-doctor";
    }

    @PostMapping("/save-doctor")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor,
                             RedirectAttributes redirectAttributes) {

        if (doctor.getName() == null || doctor.getName().trim().isEmpty() ||
                doctor.getSpecialization() == null || doctor.getSpecialization().trim().isEmpty()) {
            return "redirect:/admin/add-doctor?error=missing_fields";
        }

        boolean exists = doctorRepository.existsByNameAndSpecialization(
                doctor.getName(), doctor.getSpecialization());

        if (exists) {
            return "redirect:/admin/add-doctor?error=duplicate";
        }

        doctorRepository.save(doctor);
        return "redirect:/admin/add-doctor?success=true";
    }

    @GetMapping("/all-doctors")
    public String showAllDoctorsPage(Model model) {
        List<Doctor> list = doctorRepository.findAll();
        model.addAttribute("allDoctors", list);
        return "edit-doctor";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/admin/all-doctors?deleted";
    }
}