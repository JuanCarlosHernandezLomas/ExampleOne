package com.example.exampleOne.service;



import com.example.exampleOne.DataRepository;
import com.example.exampleOne.bo.DataBo;

import com.example.exampleOne.eo.DataEntity;
import com.example.exampleOne.exeption.ResourceNotFoundException;
import com.example.exampleOne.mapper.DataMapper;
import com.example.exampleOne.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);
    private final DataRepository dataRepository;
    private final DataMapper dataMapper;

    public DataServiceImpl(DataRepository dataRepository, DataMapper dataMapper) {
        this.dataRepository = dataRepository;
        this.dataMapper = dataMapper;
    }

    @Override
    public DataBo saveData(DataBo dataBO) {
        logger.debug("Converting DataBo to DataEntity: {}", dataBO);
        DataEntity entity = dataMapper.boToEntity(dataBO);
        DataEntity savedEntity = dataRepository.save(entity);
        logger.info("DataEntity saved in DB: {}", savedEntity);
        return dataMapper.entityToBo(savedEntity);
    }

    @Override
    public ResponseEntity<DataBo> getData(Long id) {
        logger.debug("Searching for DataEntity with ID: {}", id);
        DataEntity entity = dataRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Resource with ID {} not found", id);
                    return new ResourceNotFoundException("The resource with ID " + id + " was not found");
                });
        logger.info("Successfully retrieved DataEntity: {}", entity);
        return ResponseEntity.ok(dataMapper.entityToBo(entity));
    }

    @Override
    public List<DataBo> getAllData() {
        List<DataEntity> dataEntities = dataRepository.findAll();
        return dataEntities.stream()
                .map(dataMapper::entityToBo)
                .collect(Collectors.toList());
    }
}
