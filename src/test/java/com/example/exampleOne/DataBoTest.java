package com.example.exampleOne;




import com.example.exampleOne.bo.DataBo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DataBoTest {

    @Test
    void testConstructorAndGetters() {
        DataBo dataBo = new DataBo(1L, "Juan", 30);

        assertNotNull(dataBo);
        assertEquals(1L, dataBo.getId());
        assertEquals("Juan", dataBo.getName());
        assertEquals(30, dataBo.getEdad());
    }

    @Test
    void testNoArgsConstructor() {
        DataBo dataBo = new DataBo();

        assertNotNull(dataBo);
        assertNull(dataBo.getId());
        assertNull(dataBo.getName());
        assertEquals(0, dataBo.getEdad());
    }

    @Test
    void testSetters() {
        DataBo dataBo = new DataBo();
        dataBo.setId(2L);
        dataBo.setName("Maria");
        dataBo.setEdad(25);

        assertEquals(2L, dataBo.getId());
        assertEquals("Maria", dataBo.getName());
        assertEquals(25, dataBo.getEdad());
    }
}
