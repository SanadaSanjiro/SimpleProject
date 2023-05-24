package com.digdes.simple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private SimpleRepository simpleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(simpleRepository.existsById(1L));
        SimpleModel sm1 = new SimpleModel();
        SimpleModel sm2 = new SimpleModel();
        SimpleModel sm3 = new SimpleModel();
        sm1.setName("Питер");
        sm2.setName("Джеймс");
        sm3.setName("Джон");
        simpleRepository.save(sm1);
        simpleRepository.save(sm2);
        simpleRepository.save(sm3);
        simpleRepository.findAll().forEach(System.out::println);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}
