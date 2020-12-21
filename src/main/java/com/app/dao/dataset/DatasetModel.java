package com.app.dao.dataset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.app.dao.AuditModel;

@Entity
@Table(name = "dataset")
public class DatasetModel extends AuditModel {
    private static final long serialVersionUID = -8220251302027947197L;

    @Id
    @GeneratedValue(generator = "dataset_generator")
    @SequenceGenerator(name = "dataset_generator", sequenceName = "dataset_sequence", initialValue = 1)
    private Long id;

    @Column(unique = true, updatable = true, nullable = false)
    private String name;

    @Column(unique = false, updatable = true, nullable = true)
    private String value;

    public DatasetModel() {

    }

    public DatasetModel(Long id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
