package uz.zako.spring_files.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.spring_files.entity.MyFile;
import uz.zako.spring_files.service.MyFileService;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class Controller {

    private final MyFileService service;

    @Value("${upload}")
    private String downloadPath;

    public Controller(MyFileService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public String save(@RequestParam(name = "file") MultipartFile multipartFile) {
        return service.save(multipartFile);
    }



    @GetMapping("/get/{hashId}")
    public ResponseEntity saySalom(@PathVariable String hashId) throws MalformedURLException {

        MyFile myFile = service.findByHashId(hashId);

        System.out.println("getUploadPath = " + String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension()));
        System.out.println("URLEncoder.encode = " + URLEncoder.encode(myFile.getName()));


        return ResponseEntity.ok()
                .header(HttpHeaders.EXPIRES, "inline; fileName=" + URLEncoder.encode(myFile.getName()))
                .contentType(MediaType.parseMediaType(myFile.getContentType()))
                .body(new FileUrlResource(String.format("%s/%s.%s", myFile.getUploadPath(), myFile.getHashId(), myFile.getExtension())));

    }





}
