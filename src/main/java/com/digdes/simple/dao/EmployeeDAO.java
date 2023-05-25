package com.digdes.simple.dao;

import com.digdes.simple.dto.EmployeeDTO;
import com.digdes.simple.model.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAO {

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeModel getById(Long id) {
        try {
            EmployeeModel em = new EmployeeModel();
            em=employeeRepository.findById(id).get();
            return em;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmployeeModel create(EmployeeModel employeeModel) {
        try {
            employeeModel.setId(employeeRepository.save(employeeModel).getId());
            return employeeModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmployeeModel update(EmployeeModel employeeModel) {
        try {
            employeeRepository.save(employeeModel);
            return employeeModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EmployeeModel> getAll() {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public EmployeeModel deleteById (Long id) {
        EmployeeModel employeeModel = getById(id);
        try {
            employeeRepository.deleteById(id);
            return employeeModel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<EmployeeModel> getFiltered(EmployeeDTO dto) {
        try {
            return employeeRepository.findAll(EmloyeeSpecification.getFilteres(dto));
        } catch (Exception e) {
            e.printStackTrace();
        }
     return null;
    }
}
