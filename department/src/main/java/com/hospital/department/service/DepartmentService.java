package com.hospital.department.service;

import com.hospital.department.model.Department;
import com.hospital.department.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService implements DepartmentOperations {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Department createDepartment(Department department) {
        department.setIsActive(true);
        return departmentRepository.save(department);
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department updateDepartment(Long id, Department updatedDepartment) {
        Department existing = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        existing.setName(updatedDepartment.getName());
        existing.setHeadOfDepartment(updatedDepartment.getHeadOfDepartment());
        existing.setContactNumber(updatedDepartment.getContactNumber());
        existing.setContactEmail(updatedDepartment.getContactEmail());
        existing.setLocation(updatedDepartment.getLocation());
        existing.setDescription(updatedDepartment.getDescription());
        existing.setIsActive(updatedDepartment.getIsActive());

        return departmentRepository.save(existing);
    }

    @Override
    public void deleteDepartment(Long id) {
        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found with id: " + id);
        }
        departmentRepository.deleteById(id);
    }

    @Override
    public List<Department> searchDepartments(String keyword) {
        return departmentRepository.searchByKeyword(keyword);
    }
}

