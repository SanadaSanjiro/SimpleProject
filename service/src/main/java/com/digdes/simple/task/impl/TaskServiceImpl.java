package com.digdes.simple.task.impl;

import com.digdes.simple.amqp.MessageProducer;
import com.digdes.simple.employee.EmployeeDAO;
import com.digdes.simple.employee.EmployeeModel;
import com.digdes.simple.mail.MailMessageModel;
import com.digdes.simple.project.ProjectDAO;
import com.digdes.simple.project.ProjectModel;
import com.digdes.simple.task.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Formatter;
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
    @Autowired
    MessageProducer messageProducer;

    @Override
    public TaskDTO getById(Long id) {
        if (id==null) {throw new ResponseStatusException(HttpStatus.BAD_REQUEST);}
        TaskModel model = taskDAO.getById(id);
        if (model==null) {throw new ResponseStatusException(HttpStatus.NOT_FOUND);}
        return TaskMapper.map(model);
    }

    @Override
    public TaskDTO create(TaskCrtDTO dto) {
        if (!dtoPassCheck(dto)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        int labourCost = dto.getLaborCost(); //Трудозатраты
        LocalDate now = LocalDate.now(); // текущая дата
        LocalDate endDate = dto.getExecutionDate(); //срок исполнения
        ProjectModel project = projectDAO.getByCode(dto.getCode());
        String name = dto.getName();
        String details = dto.getDetails();
        EmployeeModel employee = employeeDAO.getById(dto.getEmployee());
        TaskStatus status = TaskStatus.NEW;
        //Получение сотрудника-автора по его логину
        EmployeeModel author = employeeDAO.getByAccount(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        TaskModel model = new TaskModel();
        model.setProject(project);
        model.setName(name);
        model.setDetails(details);
        model.setEmployee(employee);
        model.setLaborCost(labourCost);
        model.setExecutiondate(endDate);
        model.setStatus(status);
        model.setCreationdate(now);
        model.setChangedate(now);
        model.setAuthor(author);

        model=taskDAO.create(model);
        if (employee.getEMail()!=null) {
            sendMessageRequest(employee, model, author.getAccount());
        }
        return TaskMapper.map(model);
    }

    //Проверка корректности заполнения DTO
    private boolean dtoPassCheck(TaskCrtDTO dto) {
        if (dto == null || dto.getName() == null || dto.getCode() == null
                || dto.getLaborCost() == 0 || dto.getExecutionDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Проверка, что существует проект, на которой ссылается задача
        if (projectDAO.getByCode(dto.getCode())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Проверка, что существует исполнитель, если он указан
        if (employeeDAO.getById(dto.getEmployee())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int labourCost = dto.getLaborCost(); //Трудозатраты
        LocalDate now = LocalDate.now(); // текущая дата
        LocalDate planDate = now.plusDays(labourCost/workingDay); // расчетная дата завершения задачи с учетом
        // трудозатрат
        LocalDate endDate = dto.getExecutionDate(); // получанная дата завершения задачи
        //Проверка что дата исполнения не ранее текущей + трудозатраты
        if (endDate.isBefore(planDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    private void sendMessageRequest(EmployeeModel employeeModel, TaskModel model, String author) {
        MailMessageModel mailMessageModel = new MailMessageModel();
        mailMessageModel.setTo(employeeModel.getEMail());
        mailMessageModel.setSubject("Вам поступила новая задача id = " + model.getId());
        Formatter text = new Formatter();
        text.format("Пользователем %s вам была назначена задача %s (id=%d).", author, model.getName(), model.getId());
        mailMessageModel.setText(text.toString());
        messageProducer.sendMessage(mailMessageModel);
    }

    @Override
    public TaskDTO update(TaskUpdDTO dto) {
        if (updDtoPassCheck(dto)!=true) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TaskModel currentVersion = taskDAO.getById(dto.getId());
        int labourCost = dto.getLaborCost(); //Трудозатраты
        LocalDate now = LocalDate.now(); // текущая дата
        Long id = currentVersion.getId(); //id задачи
        LocalDate endDate = dto.getExecutionDate(); // получанная дата завершения задачи
        ProjectModel project = projectDAO.getByCode(dto.getCode());
        String name = dto.getName();
        String details = dto.getDetails();
        EmployeeModel employee = employeeDAO.getById(dto.getEmployee());
        TaskStatus status = currentVersion.getStatus();
        LocalDate creationDate = currentVersion.getCreationdate();
        //Получение сотрудника-автора по его логину
        EmployeeModel author = employeeDAO.getByAccount(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName());

        TaskModel model = new TaskModel();
        model.setId(id);
        model.setProject(project);
        model.setName(name);
        model.setDetails(details);
        model.setEmployee(employee);
        model.setLaborCost(labourCost);
        model.setExecutiondate(endDate);
        model.setStatus(status);
        model.setCreationdate(creationDate);
        model.setChangedate(now);
        model.setAuthor(author);

        if (currentVersion.getEmployee().getId()!=employee.getId() && employee.getEMail()!=null) {
            sendMessageRequest(employee, model, author.getAccount());
        }
        return TaskMapper.map(taskDAO.update(model));
    }

    private boolean updDtoPassCheck(TaskUpdDTO dto) {
        //Проверка корректности заполнения DTO
        if (dto == null || dto.getName() == null || dto.getCode() == null || dto.getId() == null
                || dto.getLaborCost() == 0 || dto.getExecutionDate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Проверка, что существует задача для обновления
        if (taskDAO.getById(dto.getId())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }


        //Проверка, что существует проект, на которой ссылается задача
        if (projectDAO.getByCode(dto.getCode())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //Проверка, что существует исполнитель, если он указан
        if (employeeDAO.getById(dto.getEmployee())==null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        int labourCost = dto.getLaborCost(); //Трудозатраты
        LocalDate now = LocalDate.now(); // текущая дата
        LocalDate planDate = now.plusDays(labourCost/workingDay); // расчетная дата завершения задачи с учетом
        // трудозатрат
        LocalDate endDate = dto.getExecutionDate(); // получанная дата завершения задачи
        //Проверка что дата исполнения не ранее текущей + трудозатраты
        if (endDate.isBefore(planDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return true;
    }

    @Override
    public List<TaskDTO> getFiltered(TaskSrchDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<TaskDTO> dtos = taskDAO.getFiltered(dto)
                .stream()
                .map(m-> TaskMapper.map(m))
                .toList();
        return dtos;
    }

    @Override
    public TaskDTO changeStatus(Long id) {
        if (id == null || taskDAO.getById(id) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        TaskModel model = taskDAO.getById(id);
        TaskStatus status = model.getStatus();
        switch (status) {
            case NEW: {
                status = TaskStatus.PROCESSED;
                break;
            }
            case PROCESSED: {
                status = TaskStatus.COMPLETE;
                break;
            }
            case COMPLETE: {
                status = TaskStatus.CLOSED;
                break;
            }
        }
        model.setStatus(status);
        return TaskMapper.map(taskDAO.changeStatus(model));
    }

    @Override
    public List<TaskDTO> getByPrjCode(String code) {
        if (code == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        ProjectModel projectModel = projectDAO.getByCode(code);
        if (projectModel == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<TaskDTO> dtos = taskDAO.getByProject(projectModel)
                .stream()
                .map(m-> TaskMapper.map(m))
                .toList();
        if (dtos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return dtos;
    }
}
