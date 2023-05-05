package ru.iashinme.filestore.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.service.StorageService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @Operation(summary = "Upload file")
    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public ResponseEntity<Long> uploadFile(@RequestParam("file") MultipartFile file) {
        return storageService.uploadFile(file);
    }

    @Operation(summary = "Download File")
    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long id) {
        return storageService.downloadFile(id);
    }

    @Operation(summary = "Delete File")
    @DeleteMapping("/file/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        return storageService.deleteFile(id);
    }
}
