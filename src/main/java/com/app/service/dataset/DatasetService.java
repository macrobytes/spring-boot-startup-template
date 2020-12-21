package com.app.service.dataset;

import java.util.Optional;

import com.app.dao.dataset.DatasetModel;
import com.app.dto.dataset.DatasetDto;

public interface DatasetService {
    public DatasetModel create(DatasetModel datasetModel);

    public DatasetModel patch(DatasetDto datasetDto);

    public Optional<DatasetModel> findById(long id);
}