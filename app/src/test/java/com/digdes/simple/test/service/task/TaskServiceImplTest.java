package com.digdes.simple.test.service.task;

import com.digdes.simple.employee.EmployeeDAO;
import com.digdes.simple.employee.EmployeeModel;
import com.digdes.simple.employee.EmployeeStatus;
import com.digdes.simple.project.ProjectDAO;
import com.digdes.simple.project.ProjectModel;
import com.digdes.simple.project.ProjectStatus;
import com.digdes.simple.task.*;
import com.digdes.simple.task.impl.TaskServiceImpl;
import com.digdes.simple.test.BaseTest;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskServiceImplTest extends BaseTest {
    @Mock
    TaskDAO taskDAO;

    @Mock
    EmployeeDAO employeeDAO;

    @Mock
    ProjectDAO projectDAO;

    @Mock
    Authentication auth;

    @InjectMocks
    TaskServiceImpl taskService;

    //Настраиваем мок секьюрити контекста для тестов
    @BeforeEach
    public void initSecurityContext() {
        when(auth.getName()).thenReturn("Author");
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    /****************************************************************************
     * Tests of creation
     */

    @Test
    @DisplayName("Create Task when all conditions ok")
    public void createTask_AllGoodConditions() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();
        ProjectModel projectModel = model.getProject();
        EmployeeModel employeeModel = model.getEmployee();

        //Создаем ДТО из которого собирается объект Задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        dto.setLaborCost(model.getLaborCost());

        //Создаем модель автора
        EmployeeModel authorModel = createAuthor();

        //Настраиваем правильные реакции моков
        when(projectDAO.getByCode(any())).thenReturn(projectModel);
        when(employeeDAO.getById(any())).thenReturn(employeeModel);
        when(employeeDAO.getByAccount(any())).thenReturn(authorModel);
        //Анонимный класс, получающий результат работы тестируемого метода, добавляющий id и возвращающий его обратно
        when(taskDAO.create(any(TaskModel.class))).thenAnswer(new Answer<TaskModel>() {
            public TaskModel answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TaskModel tm = (TaskModel) args[0];
                tm.setId(model.getId());
                return tm;
            }
        });

        //Вызываем метод create и проверяем правильность результатов
        TaskDTO result = taskService.create(dto);
        //System.out.println(result);

        Assertions.assertEquals(model.getId(), result.getId());
        Assertions.assertEquals(model.getProject().getCode(), result.getCode());
        Assertions.assertEquals(model.getName(), result.getName());
        Assertions.assertEquals(model.getDetails(), result.getDetails());
        Assertions.assertEquals(model.getEmployee().getId(), result.getEmployee());
        Assertions.assertEquals(model.getLaborCost(), result.getLaborCost());
        Assertions.assertEquals(model.getExecutiondate(), result.getExecutionDate());
        Assertions.assertEquals(TaskStatus.NEW.toString(), result.getStatus());
        Assertions.assertEquals(authorModel.getId(), result.getAuthor());
        Assertions.assertEquals(LocalDate.now(), result.getCreationDate());
        Assertions.assertEquals(LocalDate.now(), result.getChangeDate());
    }

    @Test
    @DisplayName("Create Task empty DTO throws exception")
    public void createTask_emptyDTO_ThrowException() {
        TaskCrtDTO dto = new TaskCrtDTO();
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task no Task name throws exception")
    public void createTask_NoTaskName_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();

        //Создаем ДТО для создания задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        dto.setLaborCost(model.getLaborCost());
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task no Project code throws exception")
    public void createTask_NoProjectCode_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();

        //Создаем ДТО для создания задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        dto.setLaborCost(model.getLaborCost());
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task no Labour Cost throws exception")
    public void createTask_NoLaborCost_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();

        //Создаем ДТО для создания задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task no Execution Date throws exception")
    public void createTask_NoExecutionDate_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();

        //Создаем ДТО для создания задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setLaborCost(model.getLaborCost());
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task no such Project throws exception")
    public void createTask_NoSuchProject_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();
        EmployeeModel employeeModel = model.getEmployee();

        //Создаем ДТО из которого собирается объект Задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        dto.setLaborCost(model.getLaborCost());

        //Создаем модель автора
        EmployeeModel authorModel = createAuthor();

        //Настраиваем правильные реакции моков
        when(projectDAO.getByCode(any())).thenReturn(null);
        when(employeeDAO.getById(any())).thenReturn(employeeModel);
        when(employeeDAO.getByAccount(any())).thenReturn(authorModel);
        //Анонимный класс, получающий результат работы тестируемого метода, добавляющий id и возвращающий его обратно
        when(taskDAO.create(any(TaskModel.class))).thenAnswer(new Answer<TaskModel>() {
            public TaskModel answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TaskModel tm = (TaskModel) args[0];
                tm.setId(model.getId());
                return tm;
            }
        });

        //Вызываем метод create и проверяем правильность результатов
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task no such Employee throws exception")
    public void createTask_NoSuchEmployee_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();
        ProjectModel projectModel = model.getProject();
        EmployeeModel employeeModel = model.getEmployee();

        //Создаем ДТО из которого собирается объект Задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        dto.setLaborCost(model.getLaborCost());

        //Создаем модель автора
        EmployeeModel authorModel = createAuthor();

        //Настраиваем правильные реакции моков
        when(projectDAO.getByCode(any())).thenReturn(projectModel);
        when(employeeDAO.getById(any())).thenReturn(null);
        when(employeeDAO.getByAccount(any())).thenReturn(authorModel);
        //Анонимный класс, получающий результат работы тестируемого метода, добавляющий id и возвращающий его обратно
        when(taskDAO.create(any(TaskModel.class))).thenAnswer(new Answer<TaskModel>() {
            public TaskModel answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TaskModel tm = (TaskModel) args[0];
                tm.setId(model.getId());
                return tm;
            }
        });

        //Вызываем метод create и проверяем правильность результатов
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    @Test
    @DisplayName("Create Task but Execution Date too short throws exception")
    public void createTask_BadExecutionDate_ThrowException() {
        //Создаем модели для возврата моками
        TaskModel model = createTaskModel();
        ProjectModel projectModel = model.getProject();
        EmployeeModel employeeModel = model.getEmployee();

        model.setLaborCost(200);

        //Создаем ДТО из которого собирается объект Задачи
        TaskCrtDTO dto = new TaskCrtDTO();
        dto.setCode(model.getProject().getCode());
        dto.setEmployee(model.getEmployee().getId());
        dto.setName(model.getName());
        dto.setDetails(model.getDetails());
        dto.setExecutionDate(model.getExecutiondate());
        dto.setLaborCost(model.getLaborCost());

        //Создаем модель автора
        EmployeeModel authorModel = createAuthor();

        //Настраиваем правильные реакции моков
        when(projectDAO.getByCode(any())).thenReturn(projectModel);
        when(employeeDAO.getById(any())).thenReturn(employeeModel);
        when(employeeDAO.getByAccount(any())).thenReturn(authorModel);
        //Анонимный класс, получающий результат работы тестируемого метода, добавляющий id и возвращающий его обратно
        when(taskDAO.create(any(TaskModel.class))).thenAnswer(new Answer<TaskModel>() {
            public TaskModel answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                TaskModel tm = (TaskModel) args[0];
                tm.setId(model.getId());
                return tm;
            }
        });

        //Вызываем метод create и проверяем правильность результатов
        Assertions.assertThrows(ResponseStatusException.class, ()->taskService.create(dto));
    }

    /****************************************************************************
     * Initialisation
     */

    private TaskModel createTaskModel() {
        Long id = 100L;
        final String firstName = "FirstName";
        final String lastName = "LastName";
        final String email = "employee@employee.com";
        EmployeeStatus employeeStatus = EmployeeStatus.ACTIVE;
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setId(id);
        employeeModel.setFirstName(firstName);
        employeeModel.setLastName(lastName);
        employeeModel.setEMail(email);
        employeeModel.setStatus(employeeStatus);

        ProjectModel projectModel = new ProjectModel();
        String code = "001";
        String name = "Project001";
        ProjectStatus projectStatus = ProjectStatus.DRAFT;
        projectModel.setCode(code);
        projectModel.setName(name);
        projectModel.setStatus(projectStatus);

        Long taskId = 1L;
        String taskName = "task01";
        String taskDescription = "some_description";
        LocalDate executionDate = LocalDate.now().plusDays(2);
        int labourCost = 8;
        TaskModel taskModel = new TaskModel();
        taskModel.setId(taskId);
        taskModel.setProject(projectModel);
        taskModel.setName(taskName);
        taskModel.setDetails(taskDescription);
        taskModel.setEmployee(employeeModel);
        taskModel.setExecutiondate(executionDate);
        taskModel.setLaborCost(labourCost);
        return taskModel;
    }

    private EmployeeModel createAuthor() {
        Long aId = 101L;
        final String afirstName = "AuthorFirstName";
        final String alastName = "AuthorLastName";
        final String account = "Author";
        EmployeeStatus employeeStatus = EmployeeStatus.ACTIVE;
        EmployeeModel authorModel = new EmployeeModel();
        authorModel.setId(aId);
        authorModel.setFirstName(afirstName);
        authorModel.setLastName(alastName);
        authorModel.setAccount(account);
        authorModel.setStatus(employeeStatus);
        return  authorModel;
    }
}
