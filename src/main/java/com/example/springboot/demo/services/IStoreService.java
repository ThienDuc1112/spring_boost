package com.example.springboot.demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStoreService {
    public String storeFile(MultipartFile file);
    public Stream<Path> loadALl();
    public byte[] readFileContent(String fileName);
    public void deleteAllFiles();
}
