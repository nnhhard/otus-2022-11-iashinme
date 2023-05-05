package ru.iashinme.filestore.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    ResponseEntity<String> uploadFile(MultipartFile file);

    ResponseEntity<byte[]> downloadFile(String guid);

    ResponseEntity<String> deleteFile(String guid);
}
