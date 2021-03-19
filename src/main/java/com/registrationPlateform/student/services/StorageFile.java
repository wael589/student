package com.registrationPlateform.student.services;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

@Service
public class StorageFile {

    private static final String DIR = "/home/smida/Bureau/formation_ads/studentProfile/src/assets/";
    public void uploadProfileImage(MultipartFile file){
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

    public void downloadFile(HttpServletResponse response,HttpServletRequest request, String filepathWitheFileNameWithExtention){
        ServletContext servletContext = request.getServletContext();
        File file2Download = new File(DIR+filepathWitheFileNameWithExtention);
        FileInputStream fis = null;
        OutputStream fos = null;
        try{
            response.setContentType(servletContext.getMimeType("directoryPath"+filepathWitheFileNameWithExtention));
            //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition",String.format("attachment; filename=\"%s\"",filepathWitheFileNameWithExtention));
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
            response.setContentLength((int)file2Download.length());
            fis = new FileInputStream(file2Download);
            fos = response.getOutputStream();
            IOUtils.copy(fis,fos);

        }catch(IOException E){

        }finally {
            try {
                if(fos!=null)
                fos.close();
                if(fis!=null)
                fis.close();
            }catch (IOException e){

            }

        }
        //How to write contents of a file to byte array in Java
    }
    public byte[] downloadFileWithArrayByte(HttpServletRequest request, HttpServletResponse response,String filepathWitheFileNameWithExtention){
        ServletContext servletContext = request.getServletContext();
        File file2Download = new File("directoryPath"+filepathWitheFileNameWithExtention);
        FileInputStream fis = null;
        byte[] arrayOfByte= null;
        try{
            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             *
             */

            /**
             * Here we have mentioned it to show inline
             */
            response.setContentType(servletContext.getMimeType("directoryPath"+filepathWitheFileNameWithExtention));
            //response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            response.setHeader("Content-Disposition",String.format("attachment; filename=\"%s\"",filepathWitheFileNameWithExtention));
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));
            response.setContentLength((int)file2Download.length());

            fis = new FileInputStream(file2Download);
            arrayOfByte = new byte[(int)file2Download.length()];
            //How to write contents of a file to byte array in Java
            fis.read(arrayOfByte);
        }catch(IOException E){

        }
        return arrayOfByte;
    }


}
