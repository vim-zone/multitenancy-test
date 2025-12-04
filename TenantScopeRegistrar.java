@Component
public class TenantScopeRegistrar implements BeanFactoryPostProcessor {

    private final TenantProvider tenantProvider;

    public TenantScopeRegistrar(TenantProvider tenantProvider) {
        this.tenantProvider = tenantProvider;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
        factory.registerScope("tenant", new TenantScope(tenantProvider));
    }
}