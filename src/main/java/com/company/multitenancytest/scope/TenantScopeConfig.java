package com.company.multitenancytest.scope;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TenantScopeConfig {

    @Bean
    public static BeanFactoryPostProcessor tenantScopeRegistrar(ApplicationContext context) {
        return factory -> {
            // do not retrieve TenantProvider here, just pass context to Scope
            factory.registerScope("tenant", new TenantScope(context));
        };
    }
}