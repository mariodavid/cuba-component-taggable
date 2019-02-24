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
class TaggingServiceBean implements TaggingService {


    @Inject
    protected DataManager dataManager

    @Inject
    protected Metadata metadata
    private static final String TAGGING_VIEW_NAME = 'tagging-view'

    @Override
    void tagEntity(Entity entity, Collection<Tag> newTags, String persistentTaggableAttribute = null) {
        setTagsForEntity(entity, newTags, persistentTaggableAttribute)
    }


    @Override
    void setTagsForEntity(Entity entity, Collection<Tag> newTags, String persistentTaggableAttribute = null) {
        CommitContext commitContext = new CommitContext()

        def existingTags = getTags(entity)

        addTagsToAddToCommitContext(newTags, existingTags, entity, persistentTaggableAttribute, null, commitContext)
        addTagsToRemoveToCommitContext(existingTags, newTags, entity, commitContext)

        dataManager.commit(commitContext)
    }

    @Override
    void setTagsForEntityWithContext(Entity entity, Collection<Tag> newTags, String tagContext) {
        CommitContext commitContext = new CommitContext()

        def existingTags = getTagsWithContext(entity, tagContext)
        String persistentTaggableAttribute = null
        addTagsToAddToCommitContext(newTags, existingTags, entity, persistentTaggableAttribute, tagContext, commitContext)
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

    private void addTagsToAddToCommitContext(Collection<Tag> newTags, Collection<Tag> existingTags, Entity entity, String persistentTaggableAttribute, String tagContext, CommitContext commitContext) {
        def tagsToAdd = newTags - existingTags
        tagsToAdd.each { tagToAdd ->
            Tagging tagging = createTagging(entity, tagToAdd, persistentTaggableAttribute, tagContext)
            commitContext.addInstanceToCommit(tagging)
        }
    }

    private Tagging findTaggingToRemove(Collection<Tagging> taggings, Tag tagToRemove) {
        taggings.find { it.tag == tagToRemove }
    }

    private Tagging createTagging(Entity entity, Tag tag, String persistentTaggableAttribute, String tagContext) {
        Tagging tagging = metadata.create(Tagging)
        tagging.setTag(tag)
        tagging.setContext(tagContext)
        tagging.setTaggable(entity)

        if (persistentTaggableAttribute) {
            tagging[persistentTaggableAttribute] = entity
        }
        tagging
    }

    @Override
    Collection<Tag> getTags(Entity entity) {
        getTaggingsForEntityWithContext(entity, null)*.tag
    }

    @Override
    Collection<Tag> getTagsWithContext(Entity entity, String tagContext) {
        return getTaggingsForEntityWithContext(entity, tagContext)*.tag
    }

    private Collection<Tagging> getTaggingsForEntityWithContext(Entity entity, String tagContext) {
        getTaggingsForEntity(entity).findAll { it.context == tagContext }
    }

    @Override
    Collection<Entity> getEntitiesWithTag(Tag tag) {
        getTaggingsForTag(tag)*.taggable.findAll { it != null }
    }

    @Override
    Collection<Tagging> getTaggingsForEntity(Entity entity) {
        LoadContext.Query query = LoadContext.createQuery('select e from ddct$Tagging e where e.taggable = :taggable')
        query.setParameter('taggable', entity)
        LoadContext<Tagging> loadContext = LoadContext.create(Tagging)
                .setQuery(query).setView(TAGGING_VIEW_NAME)
        dataManager.loadList(loadContext)
    }


    private List<Tagging> getTaggingsForTag(Tag tag) {
        LoadContext.Query query = LoadContext.createQuery('select e from ddct$Tagging e where e.tag = :tag')
        query.setParameter('tag', tag)
        LoadContext<Tagging> loadContext = LoadContext.create(Tagging)
                .setQuery(query).setView(TAGGING_VIEW_NAME)
        dataManager.loadList(loadContext)
    }

}