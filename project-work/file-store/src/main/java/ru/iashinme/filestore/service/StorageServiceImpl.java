package ru.iashinme.filestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.exception.ValidateException;
import ru.iashinme.filestore.model.FileInfo;
import ru.iashinme.filestore.respository.FileInfoRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final FileInfoRepository fileInfoRepository;

    @Override
    @Transactional
    public ResponseEntity<String> uploadFile(MultipartFile file) {
        try {
            String guid = UUID.randomUUID().toString();

            var savedFile = fileInfoRepository.save(FileInfo.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .guid(guid)
                    .dataFile(file.getBytes()).build());

            return ResponseEntity.status(HttpStatus.OK).body(savedFile.getGuid());
        } catch (Exception e) {
            throw new ValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> downloadFile(String guid) {
        try {
            FileInfo fileData = fileInfoRepository.findByGuid(guid).orElseThrow(
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
    public ResponseEntity<String> deleteFile(String guid) {
        fileInfoRepository.deleteByGuid(guid);
        return ResponseEntity.ok().body("file " + guid + " deleted!");
    }
}
