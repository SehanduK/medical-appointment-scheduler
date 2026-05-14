USE medical_db;

CREATE TABLE patients (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(10) NOT NULL,
    age INT NOT NULL
);

CREATE TABLE doctors (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    specialization VARCHAR(100),
    phone VARCHAR(10) NOT NULL
);

CREATE TABLE departments (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(100),
    head_doctor VARCHAR(100)
);

CREATE TABLE appointments (
    id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10),
    doctor_id VARCHAR(10),
    date VARCHAR(20) NOT NULL,
    time VARCHAR(10) NOT NULL,
    status VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE prescriptions (
    id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10),
    doctor_id VARCHAR(10),
    medicine VARCHAR(200) NOT NULL,
    dosage VARCHAR(100),
    date VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE medical_records (
    id VARCHAR(10) PRIMARY KEY,
    patient_id VARCHAR(10),
    diagnosis VARCHAR(200),
    treatment VARCHAR(200),
    date VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES patients(id)
);