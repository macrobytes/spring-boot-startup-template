package com.app.service.dataset;

import java.util.Optional;

import com.app.dao.dataset.DatasetModel;
import com.app.dao.dataset.DatasetRepository;
import com.app.dto.dataset.DatasetDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatasetServiceImpl implements DatasetService {
    @Autowired
    private DatasetRepository datasetRepository;

    @Override
    public DatasetModel create(DatasetModel datasetModel) {
        return datasetRepository.save(datasetModel);
    }

    @Override
    public Optional<DatasetModel> findById(long id) {
        return datasetRepository.findById(id);
    }

    @Override
    @Transactional
    public DatasetModel patch(DatasetDto datasetDto) {
        Optional<DatasetModel> model = datasetRepository.findById(datasetDto.getId());
        if (model.isPresent()) {
            DatasetModel datasetModel = model.get();
            datasetModel.setName(datasetDto.getName());
            datasetModel.setValue(datasetDto.getValue());
            return datasetRepository.save(datasetModel);
        }
        return null;
    }

}