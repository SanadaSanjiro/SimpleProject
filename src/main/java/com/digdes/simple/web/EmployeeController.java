package com.digdes.simple.web;

import com.digdes.simple.dto.EmployeeDTO;
import com.digdes.simple.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
@Tag(name= "EmployeeController", description = "Контроллер сотрудников")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Operation(summary = "Получить данные сотрудника по id")
    @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO getById(@PathVariable Long id) {
        return employeeService.getById(id);
    }

    @Operation(summary = "Создать нового сотрудника")
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO create(@RequestBody EmployeeDTO dto) { return employeeService.create(dto); }

    @Operation(summary = "Изменить данные сотрудника")
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO update(@RequestBody EmployeeDTO dto) {
        return employeeService.update(dto);
    }

    @Operation(summary = "Получить список всех сотрудников")
    @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getAll() {
        return employeeService.getAll();
    }

    @Operation(summary = "Удалить сотрудника")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeDTO delete(@PathVariable Long id) {
        return employeeService.delete(id);
    }

    @Operation(summary = "Отфильтровать сотрудников по значениям полей")
    @PutMapping(value = "/filter", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeDTO> getFiltered(@RequestBody EmployeeDTO dto) {
        System.out.println(dto);
        return employeeService.getFiltered(dto);
    }
}
