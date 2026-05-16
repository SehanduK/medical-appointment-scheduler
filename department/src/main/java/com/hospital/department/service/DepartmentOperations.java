package com.hospital.department.service;

import com.hospital.department.model.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentOperations {

    Department createDepartment(Department department);

    List<Department> getAllDepartments();

    Optional<Department> getDepartmentById(Long id);

    Department updateDepartment(Long id, Department department);

    void deleteDepartment(Long id);

    List<Department> searchDepartments(String keyword);
}

