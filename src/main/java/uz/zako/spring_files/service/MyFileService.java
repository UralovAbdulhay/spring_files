package uz.zako.spring_files.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.zako.spring_files.entity.MyFile;
import uz.zako.spring_files.repository.MyFileRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class MyFileService {


    @Autowired
    private MyFileRepository repository;

    @Value("${upload}")
    private String downloadPath;


    public String save(MultipartFile multipartFile) {

        MyFile myFile = new MyFile();

        myFile.setContentType(multipartFile.getContentType());
        myFile.setFileSize(multipartFile.getSize());
        myFile.setName(multipartFile.getOriginalFilename());
        myFile.setExtension(getExtension(myFile.getName()).toLowerCase());
        myFile.setHashId(UUID.randomUUID().toString());


        LocalDate date = LocalDate.now();

        // change value downloadPath
        String localPath = downloadPath + String.format(
                "/%d/%d/%d/%s",
                date.getYear(),
                date.getMonthValue(),
                date.getDayOfMonth(),
                myFile.getExtension().toLowerCase());

        myFile.setUploadPath(localPath);


        // downloadPath / year / month / day / extension
        File file = new File(localPath);


        // " downloadPath / year / month / day / extension "   crate directory
        if (!file.mkdirs()) {
            System.out.println(file.getAbsolutePath() + " file yaratilmadi");
        }

        // save MyFile into base
        repository.save(myFile);

        try {

            // copy bytes into new file or saving into storage
            multipartFile.transferTo(new File(file.getAbsolutePath() + "/" + String.format("%s.%s", myFile.getHashId(), myFile.getExtension())));
            return myFile.getHashId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public MyFile findByHashId(String hashId) {
        return repository.findByHashId(hashId);
    }

}
