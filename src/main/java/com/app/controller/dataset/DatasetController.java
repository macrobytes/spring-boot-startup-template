package com.app.controller.dataset;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.dao.dataset.DatasetModel;
import com.app.dto.dataset.DatasetDto;
import com.app.service.dataset.DatasetService;

@RestController
public class DatasetController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DatasetService datasetService;

    @PostMapping(path = "/dataset")
    public ResponseEntity<DatasetModel> newDataset(@Valid @RequestBody DatasetDto datasetDto) {
        DatasetModel datasetModel = modelMapper.map(datasetDto, DatasetModel.class);
        return new ResponseEntity<>(datasetService.create(datasetModel), HttpStatus.OK);
    }

    @GetMapping("/dataset/{id}")
    public ResponseEntity<DatasetModel> getDataset(@PathVariable Long id) {
        Optional<DatasetModel> model = datasetService.findById(id);
        HttpStatus status = model.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(datasetService.create(model.get()), status);
    }

    @PatchMapping("/dataset")
    public ResponseEntity<DatasetModel> patchDataset(@Valid @RequestBody DatasetDto datasetDto) {
        DatasetModel datasetModel = datasetService.patch(datasetDto);
        HttpStatus status = (null != datasetModel) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(datasetService.create(datasetModel), status);
    }
}