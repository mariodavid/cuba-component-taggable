package de.diedavids.cuba.taggable.web.test.customer

import com.haulmont.cuba.gui.components.AbstractEditor
import de.balvi.cuba.declarativecontrollers.web.editor.AnnotatableAbstractEditor
import de.diedavids.cuba.taggable.entity.example.Customer
import de.diedavids.cuba.taggable.web.WithTags

@WithTags
class CustomerEdit extends AnnotatableAbstractEditor<Customer> {
}