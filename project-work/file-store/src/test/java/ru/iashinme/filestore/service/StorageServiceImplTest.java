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
        FileInfo fileInfo = FileInfo.builder().id(-1L).guid("guid").build();
        when(fileInfoRepository.save(any())).thenReturn(fileInfo);

        ResponseEntity<String> guid = storageServiceImpl.uploadFile(mock(MultipartFile.class));

        assertThat(guid.getBody()).isEqualTo(fileInfo.getGuid());
    }

    @Test
    @DisplayName("корректно удалять файл из БД")
    public void shouldDeletedFileFromBD() {
        String guid = "guid";

        storageServiceImpl.deleteFile(guid);
        verify(fileInfoRepository, times(1)).deleteByGuid(guid);
    }

    @Test
    @DisplayName("корректно загружать файл из БД")
    public void shouldDownloadFileFromBD() {
        byte[] fileData = "123".getBytes();
        FileInfo fileInfo = FileInfo.builder().id(-1L).guid("guid").type("image/png").dataFile(fileData).build();

        when(fileInfoRepository.findByGuid(fileInfo.getGuid())).thenReturn(Optional.of(fileInfo));

        var response = storageServiceImpl.downloadFile(fileInfo.getGuid());

        assertThat(response.getBody()).isEqualTo(fileData);
    }
}
