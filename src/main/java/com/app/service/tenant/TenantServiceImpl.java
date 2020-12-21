package com.app.service.tenant;

import com.app.dao.tenant.TenantModel;
import com.app.dao.tenant.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService {
    @Autowired
    private TenantRepository tenantRepository;

    @Override
    public TenantModel create(TenantModel tenantModel) {
        return tenantRepository.save(tenantModel);
    }

}