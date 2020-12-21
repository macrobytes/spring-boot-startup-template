package com.app.dao.tenant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.app.dao.AuditModel;

@Entity
@Table(name = "tenant")
public class TenantModel extends AuditModel {
    private static final long serialVersionUID = -2359790725140348878L;

    @Id
    @GeneratedValue(generator = "tenant_generator")
    @SequenceGenerator(name = "tenant_generator", sequenceName = "tenant_sequence", initialValue = 1)
    private Long id;

    @Column(unique = true, updatable = false, nullable = false)
    private String name;

    public TenantModel() {

    }

    public TenantModel(Long id, String name) {
        this.id = id;
        this.name = name;
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
}
