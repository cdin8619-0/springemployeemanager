package com.springlearning.employeemanager.service;

import com.springlearning.employeemanager.exception.UserNotFoundException;
import com.springlearning.employeemanager.model.Employee;
import com.springlearning.employeemanager.repo.EmployeeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("John Doe", "john@example.com", "Developer", "555-1234", "http://img.url/john.png", null);
        employee.setId(1L);
    }

    @Test
    void addEmployee_setsEmployeeCodeAndSaves() {
        when(employeeRepo.save(any(Employee.class))).thenReturn(employee);

        Employee saved = employeeService.addEmployee(employee);

        assertThat(saved).isNotNull();
        assertThat(employee.getEmployeeCode()).isNotNull();
        verify(employeeRepo, times(1)).save(employee);
    }

    @Test
    void findAllEmployees_returnsAllEmployees() {
        Employee employee2 = new Employee("Jane Doe", "jane@example.com", "Manager", "555-5678", "http://img.url/jane.png", "code2");
        employee2.setId(2L);
        when(employeeRepo.findAll()).thenReturn(Arrays.asList(employee, employee2));

        List<Employee> result = employeeService.findAllEmployees();

        assertThat(result).hasSize(2);
        verify(employeeRepo, times(1)).findAll();
    }

    @Test
    void findAllEmployees_returnsEmptyList_whenNoEmployees() {
        when(employeeRepo.findAll()).thenReturn(List.of());

        List<Employee> result = employeeService.findAllEmployees();

        assertThat(result).isEmpty();
    }

    @Test
    void findEmployeeById_returnsEmployee_whenFound() {
        when(employeeRepo.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeService.findEmployeeById(1L);

        assertThat(result).isEqualTo(employee);
        assertThat(result.getName()).isEqualTo("John Doe");
        verify(employeeRepo, times(1)).findById(1L);
    }

    @Test
    void findEmployeeById_throwsException_whenNotFound() {
        when(employeeRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.findEmployeeById(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void updateEmployee_savesAndReturnsEmployee() {
        employee.setName("John Updated");
        when(employeeRepo.save(employee)).thenReturn(employee);

        Employee result = employeeService.updateEmployee(employee);

        assertThat(result.getName()).isEqualTo("John Updated");
        verify(employeeRepo, times(1)).save(employee);
    }

    @Test
    void deleteEmployee_callsDeleteById() {
        doNothing().when(employeeRepo).deleteById(1L);

        employeeService.deleteEmployee(1L);

        verify(employeeRepo, times(1)).deleteById(1L);
    }
}
