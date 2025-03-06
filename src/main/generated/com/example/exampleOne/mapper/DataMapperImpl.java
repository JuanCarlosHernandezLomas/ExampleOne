package com.example.exampleOne.mapper;

import com.example.exampleOne.bo.DataBo;
import com.example.exampleOne.eo.DataEntity;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-06T00:55:45-0600",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
public class DataMapperImpl implements DataMapper {

    @Override
    public DataBo entityToBo(DataEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DataBo dataBo = new DataBo();

        dataBo.setId( entity.getId() );
        dataBo.setName( entity.getName() );
        dataBo.setEdad( entity.getEdad() );

        return dataBo;
    }

    @Override
    public DataEntity boToEntity(DataBo bo) {
        if ( bo == null ) {
            return null;
        }

        DataEntity dataEntity = new DataEntity();

        dataEntity.setId( bo.getId() );
        dataEntity.setName( bo.getName() );
        dataEntity.setEdad( bo.getEdad() );

        return dataEntity;
    }
}
