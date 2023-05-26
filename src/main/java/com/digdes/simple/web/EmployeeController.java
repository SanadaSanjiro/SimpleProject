package com.digdes.simple.web;

import com.digdes.simple.dto.EmployeeDTO;
import com.digdes.simple.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO create(@RequestBody EmployeeDTO dto) {
        return employeeService.create(dto);
    }

    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO update(@RequestBody EmployeeDTO dto) {
        return employeeService.update(dto);
    }

    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getAll() {
        return employeeService.getAll();
    }

    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO delete(@PathVariable Long id) {
        return employeeService.delete(id);
    }

    @GetMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getFiltered(EmployeeDTO dto) {
        return employeeService.getFiltered(dto);
    }
}
