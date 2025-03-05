package com.example.exampleOne.config;


import com.example.exampleOne.bo.DataBo;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;

public class JAXBUtil {

    public static String convertToJson(DataBo dataBO) {
        try {
            JAXBContext context = JAXBContext.newInstance(DataBo.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(dataBO, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException("Error al convertir POJO a JSON", e);
        }
    }
}
