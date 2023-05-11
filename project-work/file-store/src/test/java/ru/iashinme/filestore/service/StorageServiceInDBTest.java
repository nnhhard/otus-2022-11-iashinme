package ru.iashinme.filestore.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.model.File;
import ru.iashinme.filestore.respository.FileRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StorageServiceInDB.class})
@ActiveProfiles("default")
@DisplayName("Сервис по работе с файлами должен ")
public class StorageServiceInDBTest {

    @Autowired
    private StorageServiceInDB storageServiceInDB;

    @MockBean
    private FileRepository fileRepository;

    @Test
    @DisplayName("корректно сохранять файл в БД")
    public void shouldSavedFileInBD() {
        File file = File.builder().id(-1L).build();
        when(fileRepository.save(any())).thenReturn(file);

        ResponseEntity<Long> guid = storageServiceInDB.uploadFile(mock(MultipartFile.class));

        assertThat(guid.getBody()).isEqualTo(file.getId());
    }

    @Test
    @DisplayName("корректно удалять файл из БД")
    public void shouldDeletedFileFromBD() {
        var fileId = -1L;

        storageServiceInDB.deleteFile(fileId);
        verify(fileRepository, times(1)).deleteById(fileId);
    }

    @Test
    @DisplayName("корректно загружать файл из БД")
    public void shouldDownloadFileFromBD() {
        byte[] fileData = "123".getBytes();
        File file = File.builder().id(-1L).type("image/png").dataFile(fileData).build();

        when(fileRepository.findById(file.getId())).thenReturn(Optional.of(file));

        var response = storageServiceInDB.downloadFile(file.getId());

        assertThat(response.getBody()).isEqualTo(fileData);
    }
}
