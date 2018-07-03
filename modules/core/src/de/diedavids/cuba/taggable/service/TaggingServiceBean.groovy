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
import java.util.Collection
import java.util.List
import java.util.stream.Collectors

@Service(TaggingService.NAME)
public class TaggingServiceBean implements TaggingService {


    @Inject
    protected DataManager dataManager

    @Inject
    protected Metadata metadata

    @Override
    void tagEntity(Entity entity, Collection<Tag> tags) {

        CommitContext commitContext = new CommitContext()

        def existingTags = getTags(entity)
        def taggings = getTaggings(entity)


        def tagsToAdd = tags - existingTags
        def tagsToRemove = existingTags - tags

        tagsToAdd.each {
            Tagging tagging = metadata.create(Tagging.class)
            tagging.setTag(it)
            tagging.setTaggable(entity)
            commitContext.addInstanceToCommit(tagging)
        }

        tagsToRemove.each { tagToRemove ->
            commitContext.addInstanceToRemove(taggings.find {it.tag == tagToRemove})
        }


        dataManager.commit(commitContext)
    }

    @Override
    Collection<Tag> getTags(Entity entity) {
        getTaggings(entity)*.tag
    }

    private List<Tagging> getTaggings(Entity entity) {
        LoadContext.Query query = LoadContext.createQuery('select e from ddct$Tagging e where e.taggable = :taggable')
        query.setParameter("taggable", entity, false)
        LoadContext<Tagging> loadContext = LoadContext.create(Tagging.class)
                .setQuery(query).setView("tagging-view")
        dataManager.loadList(loadContext)
    }
}