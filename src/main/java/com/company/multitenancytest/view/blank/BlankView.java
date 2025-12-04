package com.company.multitenancytest.view.blank;

import com.company.multitenancytest.scope.TenantBean;
import com.company.multitenancytest.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.multitenancy.core.TenantProvider;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "blank-view", layout = MainView.class)
@ViewController("jmul_BlankView")
@ViewDescriptor("blank-view.xml")
public class BlankView extends StandardView {
    @Autowired
    private TenantBean tenantBean;
    @ViewComponent
    private TypedTextField<Object> valueField;

    @Subscribe(id = "setButton", subject = "clickListener")
    public void onSetButtonClick(final ClickEvent<JmixButton> event) {
        tenantBean.setName(valueField.getValue());
    }

    @Subscribe(id = "getButton", subject = "clickListener")
    public void onGetButtonClick(final ClickEvent<JmixButton> event) {
        valueField.setValue(tenantBean.getName());
    }
}