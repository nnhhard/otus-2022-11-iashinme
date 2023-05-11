package ru.iashinme.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-store", path = "/api/v1")
public interface StorageServiceProxy {

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    ResponseEntity<Long> uploadFile(@RequestPart("file") MultipartFile file);

    @GetMapping("/file/{id}")
    ResponseEntity<byte[]> downloadImage(@PathVariable Long id);

    @DeleteMapping("/file/{id}")
    ResponseEntity<String> deleteFile(@PathVariable Long id);
}
