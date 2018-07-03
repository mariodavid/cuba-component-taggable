package de.diedavids.cuba.taggable.web.tagassignment;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractEditor;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.entity.example.Customer;
import de.diedavids.cuba.taggable.service.TaggingService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.UUID;

public class TagAssignment extends AbstractWindow {

    @Inject
    protected CollectionDatasource<Tag, UUID> taggableTagsDs;

    @Inject
    protected TaggingService taggingService;

    @WindowParam(required = true)
    Entity taggable;


    @Override
    public void ready() {
        taggableTagsDs.refresh(ParamsMap.of("entity", taggable));
    }

    public void commitAndClose() {
        taggingService.tagEntity(taggable, taggableTagsDs.getItems());
        close(COMMIT_ACTION_ID);
    }

    public void close() {
        close(CLOSE_ACTION_ID);
    }

}