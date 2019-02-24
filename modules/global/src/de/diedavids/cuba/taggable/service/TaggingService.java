package de.diedavids.cuba.taggable.service;


import com.haulmont.cuba.core.entity.Entity;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.entity.Tagging;

import java.util.Collection;
import java.util.List;

public interface TaggingService {
    String NAME = "ddct_TaggingService";

    void tagEntity(Entity entity, Collection<Tag> tags);
    void tagEntity(Entity entity, Collection<Tag> tags, String persistentTaggableAttribute);

    Collection<Tag> getTags(Entity entity);

    Collection<Entity> getEntitiesWithTag(Tag tag);

    Collection<Tagging> getTaggingsForEntity(Entity entity);
}