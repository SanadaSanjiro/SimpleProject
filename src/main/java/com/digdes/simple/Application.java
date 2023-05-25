package com.digdes.simple;

import com.digdes.simple.dao.*;
import com.digdes.simple.model.EmployeeModel;
import com.digdes.simple.model.EmployeeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private SimpleRepository simpleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Получаем сотрудника с id=1");
        System.out.println(employeeDAO.getById(1L));
        System.out.println("Получаем список всех сотрудников");
        System.out.println(employeeDAO.getAll());
        System.out.println("Создаем нового сотрудника");
        EmployeeModel em = new EmployeeModel();
        em.setFirstName("Федор");
        em.setLastName("Пупкин");
        em.setEMail("fedor@pupkin.biz");
        em.setPosition("Бухгалтер");
        em.setEMail("dvorink@dvornik.com");
        em.setAccount("fedunya");
        em.setStatus(EmployeeStatus.ACTIVE);
        em.setSurName("Федорович");
        em.setUid("U00001");
        em=employeeDAO.create(em);
        System.out.println(em);
        em.setEMail("buhgalter@buhgalter.com");
        System.out.println("Изменяем e-mail сотрудника");
        System.out.println(employeeDAO.update(em));
        System.out.println("Удаляем сотрудника");
        System.out.println(employeeDAO.deleteById(em.getId()));
        System.out.println("Получаем список всех сотрудников");
        System.out.println(employeeDAO.getAll());
        /**
        simpleRepository.findAll().forEach(System.out::println);
        employeeRepository.findAll().forEach(System.out::println);
        System.out.println(employeeRepository.findById(1L));
        **/
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
