package de.diedavids.cuba.taggable.web.tagassignment;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.WindowParam;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.service.TaggingService;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.UUID;

public class TagAssignment extends AbstractWindow {

    @Inject
    protected CollectionDatasource<Tag, UUID> taggableTagsDs;

    @Inject
    protected CollectionDatasource<Tag, UUID> allTagsDs;

    @Inject
    protected TaggingService taggingService;

    @WindowParam(required = true)
    Entity taggable;

    @WindowParam
    String persistentAttribute;

    @WindowParam
    String tagContext;

    @Inject
    protected Metadata metadata;

    @Override
    public void ready() {
        taggableTagsDs.refresh(ParamsMap.of("entity", taggable, "tagContext", tagContext));
    }

    public void commitAndClose() {
        if (StringUtils.isEmpty(tagContext)) {
            taggingService.setTagsForEntity(taggable, taggableTagsDs.getItems(), persistentAttribute);
        }
        else {
            taggingService.setTagsForEntityWithContext(taggable, taggableTagsDs.getItems(), persistentAttribute, tagContext);
        }

        close(COMMIT_ACTION_ID);
    }

    public void close() {
        close(CLOSE_ACTION_ID);
    }

    public void createTag() {

        Tag newTag = metadata.create(Tag.class);

        newTag.setContext(tagContext);

        openEditor("ddct$Tag.create", newTag, WindowManager.OpenType.DIALOG)
            .addCloseWithCommitListener(() -> {
            allTagsDs.refresh();
            taggableTagsDs.addItem(newTag);
        });
    }
}