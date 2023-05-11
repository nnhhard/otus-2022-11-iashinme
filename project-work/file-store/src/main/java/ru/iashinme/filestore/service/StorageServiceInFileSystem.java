package ru.iashinme.filestore.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.exception.ValidateException;
import ru.iashinme.filestore.model.FileInfo;
import ru.iashinme.filestore.respository.FileInfoRepository;

import java.io.File;
import java.nio.file.Files;
import java.util.UUID;

@Service
@Profile("file-system")
public class StorageServiceInFileSystem implements StorageService {

    private final FileInfoRepository fileInfoRepository;

    private final String path;

    public StorageServiceInFileSystem(FileInfoRepository fileInfoRepository,
                                      @Value("${file-storage.path}") String path) {
        this.fileInfoRepository = fileInfoRepository;
        this.path = path;
    }

    @Override
    @Transactional
    public ResponseEntity<Long> uploadFile(MultipartFile file) {
        try {
            String filePath = path + UUID.randomUUID();

            var fileInfo = fileInfoRepository.save(FileInfo.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .filePath(filePath).build());

            file.transferTo(new java.io.File(filePath));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(fileInfo.getId());
        } catch (Exception e) {
            throw new ValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> downloadFile(Long id) {
        try {
            FileInfo fileData = fileInfoRepository.findById(id).orElseThrow(
                    () -> new ValidateException("File not found!")
            );

            String filePath = fileData.getFilePath();
            byte[] file = Files.readAllBytes(new File(filePath).toPath());

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(fileData.getType()))
                    .body(file);
        } catch (Exception e) {
            throw new ValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteFile(Long id) {
        try {
            FileInfo fileData = fileInfoRepository.findById(id).orElseThrow(
                    () -> new ValidateException("File not found!")
            );

            String filePath = fileData.getFilePath();

            fileInfoRepository.delete(fileData);

            Files.delete(new File(filePath).toPath());

            return ResponseEntity.ok().body("file deleted!");
        } catch (Exception e) {
            throw new ValidateException("Error: " + e.getMessage());
        }
    }
}
