package com.patient.patientscheduling;

import com.patient.patientscheduling.repository.DoctorRepository;
import com.patient.patientscheduling.repository.PatientRepository;
import com.patient.patientscheduling.repository.PrescriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PatientschedulingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientschedulingApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(DoctorRepository doctorRepo, PatientRepository patientRepo, PrescriptionRepository prescriptionRepo) {
        return args -> {
            System.out.println("✅ Sample data added successfully!");
        };
    }
}
