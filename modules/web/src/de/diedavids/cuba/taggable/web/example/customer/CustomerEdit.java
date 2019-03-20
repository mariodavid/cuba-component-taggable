package de.diedavids.cuba.taggable.web.example.customer;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.taggable.entity.example.Customer;

@UiController("ddct_Customer.edit")
@UiDescriptor("customer-edit.xml")
@EditedEntityContainer("customerDc")
@LoadDataBeforeShow
public class CustomerEdit extends StandardEditor<Customer> {
}