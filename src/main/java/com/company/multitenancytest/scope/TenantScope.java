package com.company.multitenancytest.scope;

import io.jmix.multitenancy.core.TenantProvider;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.ApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TenantScope implements Scope {

    private final ApplicationContext context;

    // tenantId -> (beanName -> beanInstance)
    private final Map<String, Map<String, Object>> tenantBeans = new ConcurrentHashMap<>();

    public TenantScope(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        // retrieve TenantProvider lazily
        TenantProvider tenantProvider = context.getBean(TenantProvider.class);
        String tenantId = tenantProvider.getCurrentUserTenantId();
        if (tenantId == null) {
            throw new IllegalStateException("No tenant available");
        }

        return tenantBeans
                .computeIfAbsent(tenantId, t -> new ConcurrentHashMap<>())
                .computeIfAbsent(name, k -> objectFactory.getObject());
    }

    @Override
    public Object remove(String name) {
        TenantProvider tenantProvider = context.getBean(TenantProvider.class);
        String tenantId = tenantProvider.getCurrentUserTenantId();
        if (tenantId != null) {
            Map<String, Object> beans = tenantBeans.get(tenantId);
            if (beans != null) {
                return beans.remove(name);
            }
        }
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {}

    @Override
    public Object resolveContextualObject(String key) { return null; }

    @Override
    public String getConversationId() {
        TenantProvider tenantProvider = context.getBean(TenantProvider.class);
        return tenantProvider.getCurrentUserTenantId();
    }
}
