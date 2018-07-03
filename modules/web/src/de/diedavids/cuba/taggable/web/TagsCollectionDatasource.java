package de.diedavids.cuba.taggable.web;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.service.TaggingService;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class TagsCollectionDatasource extends CustomCollectionDatasource<Tag, UUID> {

    TaggingService taggingService = AppBeans.get(TaggingService.NAME);
    @Override
    protected Collection<Tag> getEntities(Map<String, Object> params) {
        Entity entity = (Entity) params.get("entity");
        return taggingService.getTags(entity);
    }
}
