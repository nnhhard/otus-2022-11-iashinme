package ru.iashinme.blog.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-store", path = "/api/v1")
public interface StorageServiceProxy {

    @PostMapping(value = "/file", consumes = "multipart/form-data")
    ResponseEntity<String> uploadFile(@RequestPart("file") MultipartFile file);

    @GetMapping("/file/{guid}")
    ResponseEntity<byte[]> downloadImage(@PathVariable String guid);

    @DeleteMapping("/file/{guid}")
    ResponseEntity<String> deleteFile(@PathVariable String guid);
}
