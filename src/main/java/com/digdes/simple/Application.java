package com.digdes.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ApplicationConfig.class)
public class Application implements CommandLineRunner {

    @Autowired
    private SimpleDAO simpleDAO;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(simpleDAO.getById(1L));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
