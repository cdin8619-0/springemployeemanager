package com.springlearning.employeemanager.repo;

import com.springlearning.employeemanager.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findEmployeeByEmail(String email);
    Optional<Employee> findEmployeeByEmployeeCode(String employeeCode);
}
