package com.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Base64;

import com.app.component.dev.DevSetup;
import com.app.controller.dataset.DatasetController;
import com.app.dao.dataset.DatasetModel;
import com.app.dto.dataset.DatasetDto;

import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = Replace.ANY)
class DatasetControllerTest {
    private static final String DATASET_NAME = "test-dataset";
    private static final String DATASET_VALUE = "test-dataset-value";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private RestTemplate patchRestTemplate;
    private HttpHeaders authHeader;

    @Autowired
    private DatasetController datasetController;

    @BeforeEach
    private void setup() {
        this.restTemplate = this.restTemplate.withBasicAuth(DevSetup.DEV_USERNAME, DevSetup.DEV_PASSWORD);

        String base64Creds = Base64.getEncoder()
                .encodeToString((DevSetup.DEV_USERNAME + ":" + DevSetup.DEV_PASSWORD).getBytes());
        this.authHeader = new HttpHeaders();
        this.authHeader.add("Authorization", "Basic " + base64Creds);

        this.patchRestTemplate = restTemplate.getRestTemplate();
        this.patchRestTemplate
                .setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()));
    }

    @Test
    public void contexLoads() throws Exception {
        assertThat(datasetController).isNotNull();
    }

    @Test
    public void testController() throws Exception {
        DatasetModel datasetModel = testCreateDataset();
        testGetDataset(datasetModel);
        testPatchDataset(datasetModel);
    }

    private DatasetModel testCreateDataset() {
        DatasetDto datasetDto = new DatasetDto();
        datasetDto.setName(DATASET_NAME);
        datasetDto.setValue(DATASET_VALUE);

        ResponseEntity<DatasetModel> response = this.restTemplate.postForEntity("http://localhost:" + port + "/dataset",
                datasetDto, DatasetModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(DATASET_NAME, response.getBody().getName());
        assertEquals(DATASET_VALUE, response.getBody().getValue());

        return response.getBody();
    }

    private void testGetDataset(DatasetModel datasetModel) {
        ResponseEntity<DatasetModel> response = this.restTemplate
                .getForEntity("http://localhost:" + port + "/dataset/" + datasetModel.getId(), DatasetModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(DATASET_NAME, response.getBody().getName());
        assertEquals(DATASET_VALUE, response.getBody().getValue());
    }

    private void testPatchDataset(DatasetModel datasetModel) {
        String updatedValue = "updated-value";
        DatasetDto datasetDto = new DatasetDto(datasetModel.getId(), datasetModel.getName(), updatedValue);

        ResponseEntity<DatasetModel> response = patchRestTemplate.exchange("http://localhost:" + port + "/dataset",
                HttpMethod.PATCH, new HttpEntity<DatasetDto>(datasetDto, this.authHeader), DatasetModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(DATASET_NAME, response.getBody().getName());
        assertEquals(updatedValue, response.getBody().getValue());
    }
}
