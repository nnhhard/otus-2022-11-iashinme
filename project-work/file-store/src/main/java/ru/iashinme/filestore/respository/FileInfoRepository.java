package ru.iashinme.filestore.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.filestore.model.FileInfo;

public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
