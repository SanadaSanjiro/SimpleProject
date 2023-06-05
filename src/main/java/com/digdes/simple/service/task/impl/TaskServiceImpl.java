package com.digdes.simple.service.task.impl;

import com.digdes.simple.dao.employee.EmployeeDAO;
import com.digdes.simple.dao.project.ProjectDAO;
import com.digdes.simple.dao.task.TaskDAO;
import com.digdes.simple.dto.task.TaskCrtDTO;
import com.digdes.simple.dto.task.TaskDTO;
import com.digdes.simple.dto.task.TaskSrchDTO;
import com.digdes.simple.mapping.task.TaskMapper;
import com.digdes.simple.model.employee.EmployeeModel;
import com.digdes.simple.model.project.ProjectModel;
import com.digdes.simple.model.task.TaskModel;
import com.digdes.simple.model.task.TaskStatus;
import com.digdes.simple.service.task.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final int workingDay = 8; //Предполагаемая длительность рабочего дня для пересчета трудозатрат в даты
    @Autowired
    private final TaskDAO taskDAO;
    @Autowired
    EmployeeDAO employeeDAO;
    @Autowired
    ProjectDAO projectDAO;

    @Override
    public TaskDTO getById(Long id) {
        if (id==null) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        TaskModel model = taskDAO.getById(id);
        if (model==null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return TaskMapper.map(model);
    }

    @Override
    public TaskDTO create(TaskCrtDTO dto) {
        if (dto == null || dto.getName() == null || dto.getCode() == null
                || dto.getLaborCost() == 0 || dto.getExecutionDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (projectDAO.getByCode(dto.getCode())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel project = projectDAO.getByCode(dto.getCode());

        if (employeeDAO.getById(dto.getEmployee())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        EmployeeModel employee = employeeDAO.getById(dto.getEmployee());
        int labourCost = dto.getLaborCost();
        LocalDate now = LocalDate.now(); // текущая дата
        LocalDate planDate = now.plusDays(labourCost/workingDay); // расчетная дата завершения задачи с учетом
                                                                            // трудозатрат
        LocalDate endDate = dto.getExecutionDate(); // получанная дата завершения задачи
        if (endDate.isBefore(planDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        TaskModel model = new TaskModel();
        model.setProject(project);
        model.setDetails(dto.getDetails());
        model.setEmployee(employee);
        model.setLaborCost(labourCost);
        model.setExecutionDate(endDate);
        model.setStatus(TaskStatus.NEW);
        model.setChangeDate(now);
        model.setChangeDate(now);
        // вставить сюда автора задачи
        return null;
    }

    @Override
    public TaskDTO update(TaskCrtDTO dto) {
        return null;
    }

    @Override
    public List<TaskDTO> getFiltered(TaskSrchDTO dto) {
        return null;
    }

    @Override
    public TaskDTO changeStatus(Long id) {
        return null;
    }
}
