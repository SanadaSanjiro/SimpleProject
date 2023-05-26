package com.digdes.simple.service;

import com.digdes.simple.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    EmployeeDTO getById(Long id);

    EmployeeDTO create(EmployeeDTO dto);

    EmployeeDTO update(EmployeeDTO dto);

    List<EmployeeDTO> getAll();

    EmployeeDTO delete(Long id);

    List<EmployeeDTO> getFiltered(EmployeeDTO dto);

}
