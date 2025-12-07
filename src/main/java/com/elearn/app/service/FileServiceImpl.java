package com.elearn.app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements  FileService{

    @Override
    public String save(MultipartFile file, String outputPath,String fileName) throws IOException {

        Path path = Paths.get(outputPath);

        Path filePath = Paths.get(path.toString(), file.getOriginalFilename());
        //Create output folder if not exists
        System.out.println(filePath);
        Files.createDirectory(path);
        //File writes
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return path.toString();
    }
}
