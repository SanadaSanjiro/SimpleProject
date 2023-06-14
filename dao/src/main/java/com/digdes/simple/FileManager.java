package com.digdes.simple;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String DIRECTORY_PATH = ".\\app\\src\\main\\resources\\storage";
    public void upload(byte[] resource, String name) throws IOException {
        Path path = Paths.get(DIRECTORY_PATH, name);
        Path file = Files.createFile(path);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file.toString());
            stream.write(resource);
        } finally {
            stream.close();
        }
    }
}
