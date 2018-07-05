package de.diedavids.cuba.taggable.web;

import com.haulmont.cuba.core.entity.BaseGenericIdEntity;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.KeyValueEntity;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.data.impl.CustomCollectionDatasource;
import com.haulmont.cuba.gui.data.impl.CustomValueCollectionDatasource;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.service.TaggingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class TaggedEntitiesDatasource extends CustomValueCollectionDatasource {

    TaggingService taggingService = AppBeans.get(TaggingService.NAME);
    Metadata metadata = AppBeans.get(Metadata.NAME);


    @Override
    protected Collection<KeyValueEntity> getEntities(Map<String, Object> params) {
        Tag tag = (Tag) params.get("tag");
        Collection<Entity> entitiesWithTag = taggingService.getEntitiesWithTag(tag);

        Collection<KeyValueEntity> result = new ArrayList<>();

        for (Entity entity : entitiesWithTag) {
            KeyValueEntity keyValueEntity = metadata.create(KeyValueEntity.class);
            keyValueEntity.setMetaClass(entity.getMetaClass());
            keyValueEntity.setValue("instanceName", entity.getInstanceName());
            keyValueEntity.setValue("entity", entity);
            result.add(keyValueEntity);
        }

        return result;
    }
}
