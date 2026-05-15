package com.example.medicalrecords.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.medicalrecords.model.MedicalRecord;
import com.example.medicalrecords.service.MedicalRecordService;

@Controller
public class MedicalRecordController {

    private final MedicalRecordService service;

    public MedicalRecordController(MedicalRecordService service) {
        this.service = service;
    }

    @GetMapping({"/", "/records"})
    public String viewRecords(@RequestParam(value = "search", required = false) String search, Model model) {
        List<MedicalRecord> records = service.searchRecords(search);
        model.addAttribute("records", records);
        model.addAttribute("recordForm", new MedicalRecord());
        model.addAttribute("searchQuery", search == null ? "" : search);
        return "records";
    }

    @GetMapping("/prescription/add")
    public String addPrescriptionPage(Model model) {
        MedicalRecord recordForm = new MedicalRecord();
        recordForm.setRecordId(generateRecordId());
        model.addAttribute("recordForm", recordForm);
        model.addAttribute("pageMode", "prescription");
        return "prescription";
    }

    @PostMapping("/records/add")
    public String addRecord(@ModelAttribute("recordForm") MedicalRecord record) {
        if (record.getRecordId() == null || record.getRecordId().trim().isEmpty()) {
            record.setRecordId(generateRecordId());
        }

        if (record.getPatientName() == null || record.getPatientName().trim().isEmpty()) {
            return "redirect:/records";
        }
        service.addRecord(record);
        return "redirect:/records";
    }

    @PostMapping("/prescription/add")
    public String addPrescription(@ModelAttribute("recordForm") MedicalRecord record) {
        if (record.getRecordId() == null || record.getRecordId().trim().isEmpty()) {
            record.setRecordId(generateRecordId());
        }

        if (record.getPatientName() == null || record.getPatientName().trim().isEmpty()) {
            return "redirect:/prescription/add";
        }

        service.addRecord(record);
        return "redirect:/prescription/add";
    }

    @GetMapping("/records/delete/{id}")
    public String deleteRecord(@PathVariable("id") String id) {
        service.deleteRecord(id);
        return "redirect:/records";
    }

    @PostMapping("/records/update")
    public String updateRecord(@ModelAttribute("recordForm") MedicalRecord record) {
        service.updateRecord(record);
        return "redirect:/records";
    }

    private String generateRecordId() {
        return "RX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
