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

    @PostMapping("/select-department")
    public String getDoctors(@RequestParam("departmentId") Long departmentId, Model model) {

        List<Doctor> foundDoctors = doctorRepository.findByDepartmentId(departmentId);

        System.out.println("Searching for department ID: " + departmentId);
        System.out.println("Doctors found: " + foundDoctors.size());

        model.addAttribute("doctors", foundDoctors);

        return "channeling";
    }
}