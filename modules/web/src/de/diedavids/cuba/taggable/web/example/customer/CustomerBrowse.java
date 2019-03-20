package de.diedavids.cuba.taggable.web.example.customer;

import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.ButtonsPanel;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.components.Table;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.taggable.entity.example.Customer;
import de.diedavids.cuba.taggable.web.WithTagsSupport;

import javax.inject.Inject;

@UiController("ddct_Customer.browse")
@UiDescriptor("customer-browse.xml")
@LookupComponent("customersTable")
@LoadDataBeforeShow
public class CustomerBrowse extends StandardLookup<Customer> implements WithTagsSupport {

    @Inject
    protected GroupTable<Customer> customersTable;

    @Inject
    protected ButtonsPanel buttonsPanel;

    @Override
    public Table getListComponent() {
        return customersTable;
    }

    @Override
    public boolean showTagsAsLink() {
        return true;
    }

    @Override
    public WindowManager.OpenType tagLinkOpenType() {
        return WindowManager.OpenType.NEW_TAB;
    }

    @Override
    public ButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

}