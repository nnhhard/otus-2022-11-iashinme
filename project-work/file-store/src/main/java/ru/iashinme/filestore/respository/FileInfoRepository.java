package ru.iashinme.filestore.respository;

import ru.iashinme.filestore.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

    Optional<FileInfo> findByGuid(String guid);
    void deleteByGuid(String guid);
}
