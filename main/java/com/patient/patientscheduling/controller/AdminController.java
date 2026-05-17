package com.patient.patientscheduling.controller;

import com.patient.patientscheduling.model.Department;
import com.patient.patientscheduling.model.Doctor;
import com.patient.patientscheduling.repository.DepartmentRepository;
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

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/dashboard")
    public String showAdminDashboard() {
        return "admin";
    }

    @GetMapping("/add-doctor")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        model.addAttribute("departments", departmentRepository.findAll());
        return "add-doctor";
    }

    @PostMapping("/save-doctor")
    public String saveDoctor(@ModelAttribute("doctor") Doctor doctor,
                             @RequestParam("departmentId") Long departmentId,
                             RedirectAttributes redirectAttributes) {

        if (doctor.getName() == null || doctor.getName().trim().isEmpty()) {
            return "redirect:/admin/add-doctor?error=missing_fields";
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + departmentId));
        doctor.setDepartment(department);

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