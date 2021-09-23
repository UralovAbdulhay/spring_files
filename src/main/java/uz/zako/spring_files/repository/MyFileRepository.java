package uz.zako.spring_files.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.zako.spring_files.entity.MyFile;

@Repository
public interface MyFileRepository extends JpaRepository<MyFile, Long> {

    public MyFile findByHashId(String hashId);

}
