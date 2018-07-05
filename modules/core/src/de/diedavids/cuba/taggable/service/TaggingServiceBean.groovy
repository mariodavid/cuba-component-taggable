package de.diedavids.cuba.taggable.service

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.CommitContext
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.LoadContext
import com.haulmont.cuba.core.global.Metadata
import de.diedavids.cuba.taggable.entity.Tag
import de.diedavids.cuba.taggable.entity.Tagging
import org.springframework.stereotype.Service

import javax.inject.Inject

@Service(TaggingService.NAME)
public class TaggingServiceBean implements TaggingService {


    @Inject
    protected DataManager dataManager

    @Inject
    protected Metadata metadata

    @Override
    void tagEntity(Entity entity, Collection<Tag> newTags, String persistentTaggableAttribute = null) {

        CommitContext commitContext = new CommitContext()

        def existingTags = getTags(entity)

        addTagsToAddToCommitContext(newTags, existingTags, entity, persistentTaggableAttribute, commitContext)
        addTagsToRemoveToCommitContext(existingTags, newTags, entity, commitContext)


        dataManager.commit(commitContext)
    }

    private void addTagsToRemoveToCommitContext(Collection<Tag> existingTags, Collection<Tag> newTags, Entity entity, CommitContext commitContext) {
        def tagsToRemove = existingTags - newTags
        def existingTaggings = getTaggingsForEntity(entity)
        tagsToRemove.each { tagToRemove ->
            commitContext.addInstanceToRemove(findTaggingToRemove(existingTaggings, tagToRemove))
        }
    }

    private void addTagsToAddToCommitContext(Collection<Tag> newTags, Collection<Tag> existingTags, Entity entity, String persistentTaggableAttribute, commitContext) {
        def tagsToAdd = newTags - existingTags
        tagsToAdd.each { tagToAdd ->
            Tagging tagging = createTagging(entity, tagToAdd, persistentTaggableAttribute)
            commitContext.addInstanceToCommit(tagging)
        }
    }

    private Tagging findTaggingToRemove(Collection<Tagging> taggings, Tag tagToRemove) {
        taggings.find { it.tag == tagToRemove }
    }

    private Tagging createTagging(Entity entity, Tag tag, String persistentTaggableAttribute) {
        Tagging tagging = metadata.create(Tagging.class)
        tagging.setTag(tag)
        tagging.setTaggable(entity)

        if (persistentTaggableAttribute) {
            tagging[persistentTaggableAttribute] = entity
        }
        tagging
    }

    @Override
    Collection<Tag> getTags(Entity entity) {
        getTaggingsForEntity(entity)*.tag
    }

    @Override
    Collection<Entity> getEntitiesWithTag(Tag tag) {
        return getTaggingsForTag(tag)*.taggable
    }

    private List<Tagging> getTaggingsForEntity(Entity entity) {
        LoadContext.Query query = LoadContext.createQuery('select e from ddct$Tagging e where e.taggable = :taggable')
        query.setParameter("taggable", entity, false)
        LoadContext<Tagging> loadContext = LoadContext.create(Tagging.class)
                .setQuery(query).setView("tagging-view")
        dataManager.loadList(loadContext)
    }
    private List<Tagging> getTaggingsForTag(Tag tag) {
        LoadContext.Query query = LoadContext.createQuery('select e from ddct$Tagging e where e.tag.id = :tag')
        query.setParameter("tag", tag)
        LoadContext<Tagging> loadContext = LoadContext.create(Tagging.class)
                .setQuery(query).setView("tagging-view")
        dataManager.loadList(loadContext)
    }
}