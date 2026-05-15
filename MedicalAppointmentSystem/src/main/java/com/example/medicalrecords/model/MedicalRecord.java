package com.example.medicalrecords.model;

public class MedicalRecord {

    private String recordId;
    private String patientName;
    private String medication;
    private String dosage;
    private String doctorName;
    private String dateIssued;
    private String notes;

    public MedicalRecord() {
    }

    public MedicalRecord(String recordId, String patientName, String medication, String dosage, String doctorName) {
        this.recordId = recordId;
        this.patientName = patientName;
        this.medication = medication;
        this.dosage = dosage;
        this.doctorName = doctorName;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMedication() {
        return medication;
    }

    public void setMedication(String medication) {
        this.medication = medication;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDisease() {
        return medication;
    }

    public void setDisease(String disease) {
        this.medication = disease;
    }

    public String getTreatment() {
        return dosage;
    }

    public void setTreatment(String treatment) {
        this.dosage = treatment;
    }

    public String toFileString() {
        return String.join(",", escape(recordId), escape(patientName), escape(medication), escape(dosage), escape(doctorName), escape(dateIssued), escape(notes));
    }

    public static MedicalRecord fromFileString(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",", -1);
        if (parts.length < 5) return null;

        MedicalRecord record = new MedicalRecord(unescape(parts[0]), unescape(parts[1]), unescape(parts[2]), unescape(parts[3]), unescape(parts[4]));
        if (parts.length > 5) {
            record.setDateIssued(unescape(parts[5]));
        }
        if (parts.length > 6) {
            record.setNotes(unescape(parts[6]));
        }
        return record;
    }

    private static String escape(String s) {
        return s == null ? "" : s.replace("\n", " ").replace(",", "\\,");
    }

    private static String unescape(String s) {
        return s == null ? "" : s.replace("\\,", ",");
    }
}
