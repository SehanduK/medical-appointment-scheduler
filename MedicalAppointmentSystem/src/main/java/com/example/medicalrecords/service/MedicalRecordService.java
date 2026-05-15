package com.example.medicalrecords.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.medicalrecords.model.MedicalRecord;

@Service
public class MedicalRecordService {

    private final Path dataFile;

    public MedicalRecordService() {
        this.dataFile = Paths.get("src/main/resources/data/records.txt");
        ensureDataFile();
    }

    private void ensureDataFile() {
        try {
            Files.createDirectories(dataFile.getParent());
            if (!Files.exists(dataFile)) Files.createFile(dataFile);
        } catch (IOException e) {
            throw new RuntimeException("Unable to create data file", e);
        }
    }

    public synchronized List<MedicalRecord> getAllRecords() {
        List<MedicalRecord> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                MedicalRecord r = MedicalRecord.fromFileString(line);
                if (r != null) list.add(r);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public synchronized List<MedicalRecord> searchRecords(String query) {
        String normalizedQuery = query == null ? "" : query.trim().toLowerCase();
        if (normalizedQuery.isEmpty()) {
            return getAllRecords();
        }

        List<MedicalRecord> matches = new ArrayList<>();
        for (MedicalRecord record : getAllRecords()) {
            boolean matchesId = record.getRecordId() != null && record.getRecordId().toLowerCase().contains(normalizedQuery);
            boolean matchesPatient = record.getPatientName() != null && record.getPatientName().toLowerCase().contains(normalizedQuery);
            if (matchesId || matchesPatient) {
                matches.add(record);
            }
        }
        return matches;
    }

    public synchronized void addRecord(MedicalRecord record) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile.toFile(), true))) {
            bw.write(record.toFileString());
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized boolean deleteRecord(String recordId) {
        List<MedicalRecord> list = getAllRecords();
        boolean removed = list.removeIf(r -> r.getRecordId().equals(recordId));
        if (removed) writeAll(list);
        return removed;
    }

    public synchronized boolean updateRecord(MedicalRecord updated) {
        List<MedicalRecord> list = getAllRecords();
        Optional<MedicalRecord> existing = list.stream().filter(r -> r.getRecordId().equals(updated.getRecordId())).findFirst();
        if (existing.isEmpty()) return false;
        list.removeIf(r -> r.getRecordId().equals(updated.getRecordId()));
        list.add(updated);
        writeAll(list);
        return true;
    }

    private void writeAll(List<MedicalRecord> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile.toFile(), false))) {
            for (MedicalRecord r : list) {
                bw.write(r.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
