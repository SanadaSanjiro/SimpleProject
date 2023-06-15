package com.digdes.simple;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String DIRECTORY_PATH = ".\\app\\src\\main\\resources\\storage";
    public void upload(byte[] resource, String name, String path) throws IOException {
        Path directory = Paths.get(DIRECTORY_PATH, path);
        System.out.println(path);

        if (!Files.exists(directory)) {
            System.out.println("Создаем директорию");
                createPath(directory.toAbsolutePath());
            }
        directory = Paths.get(DIRECTORY_PATH, path, name);
        Path file = Files.createFile(directory);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }

    public Resource download(String key) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH + key);
        Resource resource = new UrlResource(path.toUri());
        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            throw new IOException();
        }
    }

    private void createPath(Path path) throws IOException {
        Files.createDirectory(path);
     }
}
