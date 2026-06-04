package com.springlearning.employeemanager.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springlearning.employeemanager.exception.GlobalExceptionHandler;
import com.springlearning.employeemanager.exception.UserNotFoundException;
import com.springlearning.employeemanager.model.Employee;
import com.springlearning.employeemanager.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {EmployeeResource.class, GlobalExceptionHandler.class})
class EmployeeResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee("John Doe", "john@example.com", "Developer", "555-1234", "http://img.url/john.png", "code-abc");
        employee.setId(1L);
    }

    @Test
    void getAllEmployees_returnsOkWithList() throws Exception {
        Employee employee2 = new Employee("Jane Doe", "jane@example.com", "Manager", "555-5678", "http://img.url/jane.png", "code-xyz");
        employee2.setId(2L);
        when(employeeService.findAllEmployees()).thenReturn(Arrays.asList(employee, employee2));

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"));
    }

    @Test
    void getAllEmployees_returnsEmptyList() throws Exception {
        when(employeeService.findAllEmployees()).thenReturn(List.of());

        mockMvc.perform(get("/employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getEmployeeById_returnsOkWithEmployee() throws Exception {
        when(employeeService.findEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/employee/find/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void getEmployeeById_returns404_whenNotFound() throws Exception {
        when(employeeService.findEmployeeById(99L)).thenThrow(new UserNotFoundException("User by id 99 was not found"));

        mockMvc.perform(get("/employee/find/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User by id 99 was not found"));
    }

    @Test
    void addEmployee_returnsCreatedWithEmployee() throws Exception {
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(post("/employee/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.employeeCode").value("code-abc"));
    }

    @Test
    void updateEmployee_returnsOkWithUpdatedEmployee() throws Exception {
        employee.setName("John Updated");
        when(employeeService.updateEmployee(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(put("/employee/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"));
    }

    @Test
    void deleteEmployee_returnsOk() throws Exception {
        doNothing().when(employeeService).deleteEmployee(1L);

        mockMvc.perform(delete("/employee/delete/1"))
                .andExpect(status().isOk());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }
}
