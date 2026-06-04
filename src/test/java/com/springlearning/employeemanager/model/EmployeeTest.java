package com.springlearning.employeemanager.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeTest {

    @Test
    void defaultConstructor_createsEmptyEmployee() {
        Employee employee = new Employee();
        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isNull();
        assertThat(employee.getName()).isNull();
    }

    @Test
    void parameterizedConstructor_setsAllFields() {
        Employee employee = new Employee("Alice", "alice@example.com", "Engineer", "111-2222", "http://img/alice.png", "emp-001");

        assertThat(employee.getName()).isEqualTo("Alice");
        assertThat(employee.getEmail()).isEqualTo("alice@example.com");
        assertThat(employee.getJobTitle()).isEqualTo("Engineer");
        assertThat(employee.getPhone()).isEqualTo("111-2222");
        assertThat(employee.getImageUrl()).isEqualTo("http://img/alice.png");
        assertThat(employee.getEmployeeCode()).isEqualTo("emp-001");
    }

    @Test
    void settersAndGetters_workCorrectly() {
        Employee employee = new Employee();
        employee.setId(10L);
        employee.setName("Bob");
        employee.setEmail("bob@example.com");
        employee.setJobTitle("Analyst");
        employee.setPhone("333-4444");
        employee.setImageUrl("http://img/bob.png");
        employee.setEmployeeCode("emp-002");

        assertThat(employee.getId()).isEqualTo(10L);
        assertThat(employee.getName()).isEqualTo("Bob");
        assertThat(employee.getEmail()).isEqualTo("bob@example.com");
        assertThat(employee.getJobTitle()).isEqualTo("Analyst");
        assertThat(employee.getPhone()).isEqualTo("333-4444");
        assertThat(employee.getImageUrl()).isEqualTo("http://img/bob.png");
        assertThat(employee.getEmployeeCode()).isEqualTo("emp-002");
    }

    @Test
    void toString_containsAllFields() {
        Employee employee = new Employee("Carol", "carol@example.com", "Designer", "555-6666", "http://img/carol.png", "emp-003");
        employee.setId(3L);

        String result = employee.toString();

        assertThat(result).contains("Carol");
        assertThat(result).contains("carol@example.com");
        assertThat(result).contains("Designer");
        assertThat(result).contains("555-6666");
        assertThat(result).contains("emp-003");
    }
}
