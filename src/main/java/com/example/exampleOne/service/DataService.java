package com.example.exampleOne.service;


import com.example.exampleOne.bo.DataBo;
import com.example.exampleOne.eo.DataEntity;
import com.example.exampleOne.mapper.DataMapper;
import com.example.exampleOne.DataRepository;
import com.example.exampleOne.exeption.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataService.class);
    private final DataRepository dataRepository;

    public DataService(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    public DataBo saveData(@Validated DataBo dataBO) {
        logger.debug("Converting DataBo to DataEntity: {}", dataBO);
        DataEntity entity = DataMapper.INSTANCE.boToEntity(dataBO);
        DataEntity savedEntity = dataRepository.save(entity);
        logger.info("DataEntity saved in DB: {}", savedEntity);
        return DataMapper.INSTANCE.entityToBo(savedEntity);
    }

    public ResponseEntity<DataBo> getData(Long id) {
        logger.debug("Searching for DataEntity with ID: {}", id);
        DataEntity entity = dataRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Resource with ID {} not found", id);
                    return new ResourceNotFoundException("The resource with ID " + id + " was not found");
                });
        logger.info("Successfully retrieved DataEntity: {}", entity);
        return ResponseEntity.ok(DataMapper.INSTANCE.entityToBo(entity));

    }
    public List<DataBo> getAllData() {
        List<DataEntity> dataEntities = dataRepository.findAll();
        return dataEntities.stream().map(dataEO -> new DataBo(
                dataEO.getId(),
                dataEO.getName(),
                dataEO.getEdad()
        )).collect(Collectors.toList());
}
}