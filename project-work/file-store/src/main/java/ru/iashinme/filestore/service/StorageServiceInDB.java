package ru.iashinme.filestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.exception.ValidateException;
import ru.iashinme.filestore.model.File;
import ru.iashinme.filestore.respository.FileRepository;

@Service
@Profile("default")
@RequiredArgsConstructor
public class StorageServiceInDB implements StorageService {

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public ResponseEntity<Long> uploadFile(MultipartFile file) {
        try {

            var savedFile = fileRepository.save(File.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .dataFile(file.getBytes()).build());

            return ResponseEntity.status(HttpStatus.OK).body(savedFile.getId());
        } catch (Exception e) {
            throw new ValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> downloadFile(Long id) {
        try {
            File fileData = fileRepository.findById(id).orElseThrow(
                    () -> new ValidateException("File not found!")
            );

            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.valueOf(fileData.getType()))
                    .body(fileData.getDataFile());
        } catch (Exception e) {
            throw new ValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ResponseEntity<String> deleteFile(Long id) {
        fileRepository.deleteById(id);
        return ResponseEntity.ok().body("file deleted!");
    }
}
