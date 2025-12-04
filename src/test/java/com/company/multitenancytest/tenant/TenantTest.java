package com.company.multitenancytest.tenant;

import com.company.multitenancytest.scope.TenantBean;
import com.company.multitenancytest.test_support.AuthenticatedAsAdmin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
public class TenantTest {

    @Autowired
    private ApplicationContext ctx;

    @Test
    public final void whenRegisterScopeAndBeans_thenContextContainsFooAndBar() {

        try {
            setTenant("foo");
            TenantBean foo1 = ctx.getBean(TenantBean.class);
            TenantBean foo2 = ctx.getBean(TenantBean.class);
            assertThat(foo1, equalTo(foo2)); // same tenant

            setTenant("bar");
            TenantBean bar1 = ctx.getBean(TenantBean.class);
            assertThat(foo1, not(equalTo(bar1))); // different tenant

        }
        finally {
            ctx.close();
        }
    }
}
