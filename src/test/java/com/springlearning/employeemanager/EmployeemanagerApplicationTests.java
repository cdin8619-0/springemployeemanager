package com.springlearning.employeemanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class EmployeemanagerApplicationTests {

    @Test
    void contextLoads() {
    }
}
