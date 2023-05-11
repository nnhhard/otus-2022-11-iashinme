package ru.iashinme.filestore.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iashinme.filestore.model.File;

public interface FileRepository extends JpaRepository<File, Long> {
}