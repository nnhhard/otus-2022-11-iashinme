package ru.iashinme.filestore.rest;

import ru.iashinme.filestore.service.StorageService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @Operation(summary = "Upload file")
    @PostMapping(value = "/file", consumes = "multipart/form-data")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        return storageService.uploadFile(file);
    }

    @Operation(summary = "Download File")
    @GetMapping("/file/{guid}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String guid) {
        return storageService.downloadFile(guid);
    }

    @Operation(summary = "Delete File")
    @DeleteMapping("/file/{guid}")
    public ResponseEntity<String> deleteFile(@PathVariable String guid) {
        return storageService.deleteFile(guid);
    }
}
