package de.diedavids.cuba.taggable.service;


import com.haulmont.cuba.core.entity.Entity;
import de.diedavids.cuba.taggable.entity.Tag;

import java.util.Collection;

public interface TaggingService {
    String NAME = "ddct_TaggingService";

    void tagEntity(Entity entity, Collection<Tag> tags);
    void tagEntity(Entity entity, Collection<Tag> tags, String persistentTaggableAttribute);

    Collection<Tag> getTags(Entity entity);
    Collection<Entity> getEntitiesWithTag(Tag tag);
}