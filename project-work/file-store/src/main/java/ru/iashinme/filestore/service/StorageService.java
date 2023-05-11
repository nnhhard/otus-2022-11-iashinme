package ru.iashinme.filestore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    ResponseEntity<Long> uploadFile(MultipartFile file);

    ResponseEntity<byte[]> downloadFile(Long id);

    ResponseEntity<String> deleteFile(Long id);
}
