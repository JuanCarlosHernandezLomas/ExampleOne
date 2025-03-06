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
public interface DataService {
DataBo saveData(DataBo dataBo);
ResponseEntity<DataBo> getData(Long id);
List<DataBo>getAllData();

}