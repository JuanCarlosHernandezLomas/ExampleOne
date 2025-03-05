package com.example.exampleOne.mapper;

import com.example.exampleOne.bo.DataBo;
import com.example.exampleOne.eo.DataEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-04T23:08:42-0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
public class DataMapperImpl implements DataMapper {

    @Override
    public DataBo entityToBo(DataEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        int edad = 0;

        id = entity.getId();
        name = entity.getName();
        edad = entity.getEdad();

        DataBo dataBo = new DataBo( id, name, edad );

        return dataBo;
    }

    @Override
    public DataEntity boToEntity(DataBo bo) {
        if ( bo == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        int edad = 0;

        id = bo.getId();
        name = bo.getName();
        edad = bo.getEdad();

        DataEntity dataEntity = new DataEntity( id, name, edad );

        return dataEntity;
    }
}
