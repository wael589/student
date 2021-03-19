package com.registrationPlateform.student.controller;

import com.registrationPlateform.student.services.StorageFile;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/registration")
public class FileController {

    @Autowired
    private StorageFile storageFile;

    @PostMapping(value = "/image/upload")
    public void uploadUserProfileImage(@RequestParam("file") MultipartFile file){
//        storageFile.uploadProfileImage(file);
        try {
            //method 1
//            File myFile = new File("/home/smida/Bureau/formation_ads/studentProfile/src/assets/" + file.getOriginalFilename());
//            myFile.createNewFile();
//            FileOutputStream fos = new FileOutputStream(myFile);
//            fos.write(file.getBytes());
//            fos.close();
            /*
            method 2
             */
            Path p = Paths.get("/home/smida/Bureau/formation_ads/studentProfile/src/assets/" + file.getOriginalFilename());
            Files.createFile(p);
            Files.write(p,file.getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @GetMapping(value = "/image/download")
    public void downloadImage(@RequestParam("fileName")String fileName, HttpServletResponse response, HttpServletRequest request){
        storageFile.downloadFile(response,request,fileName);
    }
}
