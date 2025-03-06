package com.example.exampleOne;



import com.example.exampleOne.bo.DataBo;

import com.example.exampleOne.eo.DataEntity;
import com.example.exampleOne.exeption.ResourceNotFoundException;
import com.example.exampleOne.mapper.DataMapper;
import com.example.exampleOne.service.DataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataServiceImplTest {

    @Mock
    private DataRepository dataRepository;

    @Mock
    private DataMapper dataMapper;  // ✅ Ahora mockeamos DataMapper

    @InjectMocks
    private DataServiceImpl dataService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveData_Success() {
        DataBo dataBo = new DataBo(1L, "Juan", 30);
        DataEntity entity = new DataEntity(1L, "Juan", 30);

        when(dataMapper.boToEntity(any(DataBo.class))).thenReturn(entity);
        when(dataRepository.save(any(DataEntity.class))).thenReturn(entity);
        when(dataMapper.entityToBo(any(DataEntity.class))).thenReturn(dataBo);

        DataBo savedData = dataService.saveData(dataBo);

        assertNotNull(savedData);
        assertEquals("Juan", savedData.getName());
        assertEquals(30, savedData.getEdad());
        verify(dataRepository, times(1)).save(any(DataEntity.class));
    }

    @Test
    void testGetData_Success() {
        DataEntity entity = new DataEntity(1L, "Carlos", 40);
        DataBo dataBo = new DataBo(1L, "Carlos", 40);

        when(dataRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(dataMapper.entityToBo(entity)).thenReturn(dataBo);

        ResponseEntity<DataBo> response = dataService.getData(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Carlos", response.getBody().getName());
        verify(dataRepository, times(1)).findById(1L);
    }

    @Test
    void testGetData_NotFound() {
        when(dataRepository.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            dataService.getData(999L);
        });

        assertEquals("The resource with ID 999 was not found", exception.getMessage());
        verify(dataRepository, times(1)).findById(999L);
    }

    @Test
    void testGetAllData() {
        List<DataEntity> mockEntities = Arrays.asList(
                new DataEntity(1L, "Juan", 30),
                new DataEntity(2L, "Maria", 25)
        );

        List<DataBo> expectedDataBo = Arrays.asList(
                new DataBo(1L, "Juan", 30),
                new DataBo(2L, "Maria", 25)
        );

        when(dataRepository.findAll()).thenReturn(mockEntities);
        when(dataMapper.entityToBo(mockEntities.get(0))).thenReturn(expectedDataBo.get(0));
        when(dataMapper.entityToBo(mockEntities.get(1))).thenReturn(expectedDataBo.get(1));

        List<DataBo> result = dataService.getAllData();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Juan", result.get(0).getName());
        assertEquals("Maria", result.get(1).getName());
        verify(dataRepository, times(1)).findAll();
    }
}
