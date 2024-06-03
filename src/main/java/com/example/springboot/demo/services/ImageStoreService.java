package com.example.springboot.demo.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStoreService implements IStoreService{
    private final Path storageFolder = Paths.get("uploads");

    private boolean isImageFile(MultipartFile fileName){
        String fileExtension = FilenameUtils.getExtension(fileName.getOriginalFilename());
        return Arrays.asList(new String[] {"png", "jpg", "jpeg"})
                .contains(fileExtension.trim().toLowerCase());
    }

    public ImageStoreService(){
        try{
            Files.createDirectories(storageFolder);
        }catch (IOException exception){
                throw new RuntimeException("cannot initialize storage", exception);
        }
    }
    @Override
    public String storeFile(MultipartFile file) {
        try{
            if(file.isEmpty()){
                throw  new RuntimeException("the file is empty");
            }
            if(!isImageFile(file)){
                throw  new RuntimeException("Only images is accepted");
            }
            float fileSizeInMegabytes = file.getSize() / 1_000_000.0f;
            if(fileSizeInMegabytes > 5.0f){
                throw  new RuntimeException("The size of file must be lower than 5Mb");
            }
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String generatedFileName = UUID.randomUUID().toString().replace("-","");
            generatedFileName = generatedFileName + "." + fileExtension;
            Path destination = this.storageFolder.resolve(
              Paths.get(generatedFileName)).normalize().toAbsolutePath();

            if(!destination.getParent().equals(this.storageFolder.toAbsolutePath())){
                throw new RuntimeException("cannot store file outside current directory");
            }
            try(InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            return  generatedFileName;

        }catch (IOException exception){
            throw  new RuntimeException("Failed to store file", exception);
        }
    }

    @Override
    public Stream<Path> loadALl() {
        try{
            return Files.walk(this.storageFolder,1)
                    .filter(path -> !path.equals(this.storageFolder))
                    .map(this.storageFolder::relativize);
        }catch (IOException exception){
            throw new RuntimeException("Failed to load stored files", exception);
        }
    }

    @Override
    public byte[] readFileContent(String fileName) {
        try{
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() && resource.isReadable()){
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }else{
                throw new RuntimeException("Couldn't read file" + fileName);
            }

        }catch (IOException exception){
            throw new RuntimeException("Could not read file", exception);
        }
    }

    @Override
    public void deleteAllFiles() {

    }
}
