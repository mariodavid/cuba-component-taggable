package de.diedavids.cuba.taggable.web.example.customer;
/*
import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.entity.example.Customer;
import de.diedavids.cuba.taggable.service.TaggingService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class CustomerTag extends AbstractEditor<Customer> {

    @Inject
    protected CollectionDatasource<Tag, UUID> customerTagsDs;

    @Inject
    protected TaggingService taggingService;


    @Override
    protected void postInit() {
        super.postInit();
        customerTagsDs.refresh(ParamsMap.of("entity", getItem()));

    }

    @Override
    protected boolean preCommit() {

        Collection<Tag> items = customerTagsDs.getItems();

        taggingService.tagEntity(getItem(), items);

        return super.preCommit();
    }
}
*/