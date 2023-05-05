package ru.iashinme.filestore.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import ru.iashinme.filestore.model.FileInfo;
import ru.iashinme.filestore.respository.FileInfoRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StorageServiceImpl.class})
@DisplayName("Сервис по работе с файлами должен ")
public class StorageServiceImplTest {

    @Autowired
    private StorageServiceImpl storageServiceImpl;

    @MockBean
    private FileInfoRepository fileInfoRepository;

    @Test
    @DisplayName("корректно сохранять файл в БД")
    public void shouldSavedFileInBD() {
        FileInfo fileInfo = FileInfo.builder().id(-1L).build();
        when(fileInfoRepository.save(any())).thenReturn(fileInfo);

        ResponseEntity<Long> guid = storageServiceImpl.uploadFile(mock(MultipartFile.class));

        assertThat(guid.getBody()).isEqualTo(fileInfo.getId());
    }

    @Test
    @DisplayName("корректно удалять файл из БД")
    public void shouldDeletedFileFromBD() {
        var fileId = -1L;

        storageServiceImpl.deleteFile(fileId);
        verify(fileInfoRepository, times(1)).deleteById(fileId);
    }

    @Test
    @DisplayName("корректно загружать файл из БД")
    public void shouldDownloadFileFromBD() {
        byte[] fileData = "123".getBytes();
        FileInfo fileInfo = FileInfo.builder().id(-1L).type("image/png").dataFile(fileData).build();

        when(fileInfoRepository.findById(fileInfo.getId())).thenReturn(Optional.of(fileInfo));

        var response = storageServiceImpl.downloadFile(fileInfo.getId());

        assertThat(response.getBody()).isEqualTo(fileData);
    }
}
