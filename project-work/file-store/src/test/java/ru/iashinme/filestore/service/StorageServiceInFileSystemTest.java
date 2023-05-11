package ru.iashinme.filestore.service;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.model.FileInfo;
import ru.iashinme.filestore.respository.FileInfoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StorageServiceInFileSystem.class})
@ActiveProfiles("file-system")
@DisplayName("Сервис по работе с файлами должен ")
public class StorageServiceInFileSystemTest {

    @Autowired
    private StorageServiceInFileSystem storageServiceInFileSystem;

    @MockBean
    private FileInfoRepository fileInfoRepository;

    @Test
    @DisplayName("корректно сохранять информацию о файле")
    public void shouldSavedFileInBD() {
        FileInfo file = FileInfo.builder().id(-1L).build();
        when(fileInfoRepository.save(any())).thenReturn(file);

        ResponseEntity<Long> guid = storageServiceInFileSystem.uploadFile(mock(MultipartFile.class));

        assertThat(guid.getBody()).isEqualTo(file.getId());
    }

    @Test
    @DisplayName("корректно удалять информацию о файле из БД и файл из файловой системы")
    public void shouldDeletedFileFromBD() throws IOException {
        FileInfo file = FileInfo.builder().id(-1L).filePath("C://tmp//123").build();
        Files.createFile(new File(file.getFilePath()).toPath());

        when(fileInfoRepository.findById(file.getId())).thenReturn(Optional.of(file));

        storageServiceInFileSystem.deleteFile(file.getId());
        verify(fileInfoRepository, times(1)).delete(file);
    }

    @Test
    @DisplayName("корректно загружать файл")
    public void shouldDownloadFileFromBD() throws IOException {
        byte[] fileData = UUID.randomUUID().toString().getBytes();
        FileInfo file = FileInfo.builder().id(-1L).type("image/png").filePath("C://tmp//123").build();

        File filePath = new File(file.getFilePath());
        FileUtils.writeByteArrayToFile(filePath, fileData);

        when(fileInfoRepository.findById(file.getId())).thenReturn(Optional.of(file));

        var response = storageServiceInFileSystem.downloadFile(file.getId());

        assertThat(response.getBody()).isEqualTo(fileData);

        FileUtils.delete(filePath);
    }
}
