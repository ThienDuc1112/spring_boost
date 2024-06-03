package com.example.springboot.demo.controllers;

import com.example.springboot.demo.DTOs.Common.ResponseObject;
import com.example.springboot.demo.services.IStoreService;
import com.example.springboot.demo.services.ImageStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/FileUpload")
public class FileUploadController {
    @Autowired
    private IStoreService storeService;

    @PostMapping("")
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFile = storeService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Upload image successfully", generatedFile)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Failed to upload", "")
            );
        }
    }

    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storeService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getAllFiles")
    public ResponseEntity<ResponseObject> getUploadedFiles(){
        try{
            List<String> urls = storeService.loadALl()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "readFile", path.getFileName().toString()).build().toUri().toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("success", "get all successfully", urls));
        }catch (Exception exception){
            return ResponseEntity.notFound().build();
        }
    }
}
