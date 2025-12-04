package com.company.multitenancytest.scope;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("tenant")
public class TenantBean {

    private static final Logger log = LoggerFactory.getLogger(TenantBean.class);

    private String name = "N/A";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
