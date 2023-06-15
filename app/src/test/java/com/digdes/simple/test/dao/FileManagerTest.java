package com.digdes.simple.test.dao;

import com.digdes.simple.FileManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class FileManagerTest {
    /**
    final String DIRECTORY_PATH = ".\\src\\main\\resources\\storage\\test.txt";
    Path path = Paths.get(DIRECTORY_PATH);
    File file = path.toFile();

    @BeforeEach
    void before() {
        deleteTestFile();
    }

    @AfterEach
    void after() {
        deleteTestFile();
    }

    @Test
    public void uploadTest() {
        String string = "123";
        byte[] resource = string.getBytes(StandardCharsets.UTF_8);
        FileManager fm = new FileManager();
        try {
            fm.upload(resource, "test.txt", file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertTrue(file.exists());
    }

    private void deleteTestFile() {
        if (file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    **/
}
