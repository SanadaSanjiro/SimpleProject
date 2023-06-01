package com.digdes.simple.testing.service.employee;

import com.digdes.simple.dao.employee.EmployeeDAO;
import com.digdes.simple.dto.employee.EmployeeCrtDTO;
import com.digdes.simple.dto.employee.EmployeeViewDTO;
import com.digdes.simple.model.employee.EmployeeModel;
import com.digdes.simple.model.employee.EmployeeStatus;
import com.digdes.simple.service.EmployeeService;
import com.digdes.simple.service.PassEncoder;
import com.digdes.simple.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class EmployeeServiceImplTest {
    @Mock
    EmployeeDAO employeeDAO;

    @Mock
    PassEncoder pe;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    public void employeeNotExists() {
        final String firstName = "FirstName";
        final String lastName = "LastName";
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName(firstName);
        employeeModel.setLastName(lastName);
        EmployeeModel dbModel = new EmployeeModel();
        dbModel.setId(1L);
        dbModel.setFirstName(firstName);
        dbModel.setLastName(lastName);
        dbModel.setStatus(EmployeeStatus.ACTIVE);
        when(employeeDAO.create(any())).thenReturn(dbModel);
        EmployeeCrtDTO dto = new EmployeeCrtDTO();
        dto.setFirstname(firstName);
        dto.setLastname(lastName);
        System.out.println(dto);
        EmployeeViewDTO result = employeeService.create(dto);
        System.out.println(result);
        verify(employeeDAO, Mockito.times(1)).create(Mockito.any());
        Assertions.assertEquals(firstName, result.getFirstname());
        Assertions.assertEquals(lastName, result.getLastname());
        Assertions.assertEquals(result.getStatus(), "ACTIVE");
        Assertions.assertNotNull(result.getId());
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void getAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void getFiltered() {
    }
}
