package de.diedavids.cuba.taggable.service;


import com.haulmont.cuba.core.entity.Entity;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.entity.example.Customer;

import java.util.Collection;

public interface TaggingService {
    String NAME = "ddct_TaggingService";

    void tagEntity(Entity entity, Collection<Tag> tags);

    Collection<Tag> getTags(Entity entity);
}