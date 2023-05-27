package com.digdes.simple.service.impl;

import com.digdes.simple.dao.EmployeeDAO;
import com.digdes.simple.dto.EmployeeDTO;
import com.digdes.simple.mapping.EmployeeMapper;
import com.digdes.simple.model.EmployeeModel;
import com.digdes.simple.service.EmployeeService;
import com.digdes.simple.service.PassEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    private final PassEncoder passEncoder;

    @Override
    public EmployeeDTO getById(Long id) {
        EmployeeModel model = employeeDAO.getById(id);
        if (model==null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return EmployeeMapper.map(model);
    }

    @Override
    public EmployeeDTO create(EmployeeDTO dto) {
        dto.setPassword(passEncoder.encode(dto.getPassword()));
        EmployeeModel model = employeeDAO.create(EmployeeMapper.map(dto));
        return EmployeeMapper.map(model);
    }

    @Override
    public EmployeeDTO update(EmployeeDTO dto) {
        EmployeeModel model = employeeDAO.update(EmployeeMapper.map(dto));
        return EmployeeMapper.map(model);
    }

    @Override
    public List<EmployeeDTO> getAll() {
        List<EmployeeDTO> dtos = employeeDAO.getAll()
                .stream()
                .map(m-> EmployeeMapper.map(m))
                .toList();
        return dtos;
    }

    @Override
    public EmployeeDTO delete(Long id) {
        EmployeeModel model = employeeDAO.deleteById(id);
        if (model==null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return EmployeeMapper.map(model);
    }

    @Override
    public List<EmployeeDTO> getFiltered(EmployeeDTO dto) {
        List<EmployeeDTO> dtos = employeeDAO.getFiltered(dto)
                .stream()
                .map(m-> EmployeeMapper.map(m))
                .toList();
        return dtos;
    }
}
