package com.digdes.simple.employee;


import java.util.List;

public interface EmployeeService {

    EmployeeViewDTO getById(Long id);

    EmployeeViewDTO create(EmployeeCrtDTO dto);

    EmployeeViewDTO update(EmployeeUpdDTO dto);

    List<EmployeeViewDTO> getAll();

    EmployeeViewDTO delete(Long id);

    List<EmployeeViewDTO> getFiltered(EmployeeSrchDTO dto);

}
