package com.example.exampleOne;



import com.example.exampleOne.bo.DataBo;
import com.example.exampleOne.controller.DataController;
import com.example.exampleOne.service.DataService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DataControllerTest {

    @Mock
    private DataService dataService;

    @InjectMocks
    private DataController dataController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testHealthCheck() {
        ResponseEntity<String> response = dataController.healthCheck();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("API working correctly", response.getBody());
    }

    @Test
    void testSaveData_Success() {
        DataBo dataBo = new DataBo(1L, "Juan", 30);
        when(dataService.saveData(any(DataBo.class))).thenReturn(dataBo);

        ResponseEntity<DataBo> response = dataController.saveData(dataBo);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(dataBo, response.getBody());
        verify(dataService, times(1)).saveData(any(DataBo.class));
    }

    @Test
    void testSaveData_ValidationFailure() {
        DataBo invalidData = new DataBo(1L, "", -1); // Datos inválidos
        when(dataService.saveData(any(DataBo.class))).thenThrow(new ConstraintViolationException("Validation error", null));

        assertThrows(ConstraintViolationException.class, () -> dataController.saveData(invalidData));
        verify(dataService, times(1)).saveData(any(DataBo.class));
    }

    @Test
    void testGetAllData() {
        List<DataBo> mockData = Arrays.asList(
                new DataBo(1L, "Juan", 30),
                new DataBo(2L, "Maria", 25)
        );
        when(dataService.getAllData()).thenReturn(mockData);

        ResponseEntity<List<DataBo>> response = dataController.getAllData();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        verify(dataService, times(1)).getAllData();
    }

    @Test
    void testGetData_Success() {
        DataBo dataBo = new DataBo(1L, "Carlos", 40);
        when(dataService.getData(1L)).thenReturn(ResponseEntity.ok(dataBo));

        ResponseEntity<DataBo> response = dataController.getData(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Carlos", response.getBody().getName());
        verify(dataService, times(1)).getData(1L);
    }

    @Test
    void testGetData_NotFound() {
        when(dataService.getData(999L)).thenReturn(ResponseEntity.notFound().build());

        ResponseEntity<DataBo> response = dataController.getData(999L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        verify(dataService, times(1)).getData(999L);
    }
}
