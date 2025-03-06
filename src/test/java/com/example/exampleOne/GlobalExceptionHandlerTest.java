package com.example.exampleOne;


import com.example.exampleOne.bo.DataBo;
import com.example.exampleOne.config.SampleController;
import com.example.exampleOne.exeption.GlobalExceptionHandler;
import com.example.exampleOne.exeption.ResourceNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private ConstraintViolation<?> violation1;

    @Mock
    private ConstraintViolation<?> violation2;

    @Mock
    private Path path1;

    @Mock
    private Path path2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
    }

    @Test
    void testHandleValidationException() throws NoSuchMethodException {
        // Mock de errores de validación
        FieldError fieldError1 = new FieldError("dataBo", "name", "Name cannot be empty");
        FieldError fieldError2 = new FieldError("dataBo", "edad", "Age must be greater than 0");

        when(bindingResult.getFieldErrors()).thenReturn(Arrays.asList(fieldError1, fieldError2));

        // ✅ Crear un MethodParameter real
        Method method = SampleController.class.getMethod("sampleMethod", DataBo.class);
        MethodParameter methodParameter = new MethodParameter(method, 0);

        // ✅ Ahora pasamos el MethodParameter válido
        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationException(ex);

        // Verificaciones
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Name cannot be empty", response.getBody().get("name"));
        assertEquals("Age must be greater than 0", response.getBody().get("edad"));
    }


    @Test
    void testHandleResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleResourceNotFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Resource not found", response.getBody().get("error"));
    }

    @Test
    void testHandleGeneralException() {
        Exception ex = new Exception("Unexpected error");

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleGeneralException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().get("error").contains("Ocurrió un error inesperado"));
    }

    @Test
    void testHandleConstraintViolationException() {
        when(violation1.getPropertyPath()).thenReturn(path1);
        when(violation2.getPropertyPath()).thenReturn(path2);
        when(path1.toString()).thenReturn("name");
        when(path2.toString()).thenReturn("edad");

        when(violation1.getMessage()).thenReturn("Name cannot be blank");
        when(violation2.getMessage()).thenReturn("Age must be between 1 and 120");

        Set<ConstraintViolation<?>> violations = new HashSet<>(Arrays.asList(violation1, violation2));
        ConstraintViolationException ex = new ConstraintViolationException("Validation failed", violations);

        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleConstraintViolationException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Name cannot be blank", response.getBody().get("name"));
        assertEquals("Age must be between 1 and 120", response.getBody().get("edad"));
    }
}
