package com.example.exampleOne;


import com.example.exampleOne.eo.DataEntity;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class DataEntityTest {

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testNoArgsConstructor() {
        DataEntity entity = new DataEntity();
        assertNotNull(entity);
    }

    @Test
    void testAllArgsConstructor() {
        DataEntity entity = new DataEntity(1L, "John Doe", 30);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("John Doe", entity.getName());
        assertEquals(30, entity.getEdad());
    }

    @Test
    void testSettersAndGetters() {
        DataEntity entity = new DataEntity();

        entity.setId(2L);
        entity.setName("Jane Doe");
        entity.setEdad(25);

        assertEquals(2L, entity.getId());
        assertEquals("Jane Doe", entity.getName());
        assertEquals(25, entity.getEdad());
    }

    @Test
    void testValidationConstraints() {
        DataEntity entity = new DataEntity();

        // Prueba de restricción de nombre (menor de 3 caracteres)
        entity.setName("Jo");
        Set<ConstraintViolation<DataEntity>> violations = validator.validate(entity);
        assertFalse(violations.isEmpty(), "Se esperaba una violación de validación para nombre < 3 caracteres");

        // Prueba de restricción de nombre (mayor de 50 caracteres)
        entity.setName("JohnDoeJohnDoeJohnDoeJohnDoeJohnDoeJohnDoeJohnDoe");
        violations = validator.validate(entity);
        assertFalse(violations.isEmpty(), "Se esperaba una violación de validación para nombre > 50 caracteres");

        // Prueba de restricción de edad (menor de 1)
        entity.setEdad(0);
        violations = validator.validate(entity);
        assertFalse(violations.isEmpty(), "Se esperaba una violación de validación para edad < 1");

        // Prueba de restricción de edad (mayor de 120)
        entity.setEdad(121);
        violations = validator.validate(entity);
        assertFalse(violations.isEmpty(), "Se esperaba una violación de validación para edad > 120");
    }
}
