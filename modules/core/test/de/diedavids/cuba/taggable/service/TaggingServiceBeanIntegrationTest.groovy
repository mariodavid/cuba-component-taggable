package de.diedavids.cuba.taggable.service


import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.taggable.AbstractDbIntegrationTest
import de.diedavids.cuba.taggable.entity.Tag
import de.diedavids.cuba.taggable.entity.Tagging
import org.junit.Before
import org.junit.Test

import static org.assertj.core.api.Assertions.assertThat

class TaggingServiceBeanIntegrationTest extends AbstractDbIntegrationTest{

    private TaggingService taggingService
    private User admin
    private Tag cool
    private Tag small
    private Tag tall

    @Before
    void before() {
        taggingService =  AppBeans.get(TaggingService)

        clearTable("DDCT_TAGGING")
        clearTable("DDCT_TAG")

        admin = loadUser('admin')

        cool = tag("cool")
        small = tag("small")
        tall = tag("tall")
    }


    @Test
    void "setTagsForEntity tags an entity with only the tags passed as a parameter"() {

        //when:
        taggingService.setTagsForEntity(admin, [cool])

        //then:
        assertThat(taggingService.getTags(admin)).hasSize(1)
        assertThat(taggingService.getTags(admin)).contains(cool)
    }

    @Test
    void "setTagsForEntity tags an entity with only the tags passed as a parameter under the global context"() {

        //when:
        taggingService.setTagsForEntity(admin, [cool])

        //and:
        taggingService.setTagsForEntityWithContext(admin, [small, tall], "size")

        //and:
        def globalContextTags = taggingService.getTags(admin)
        def sizeTags = taggingService.getTagsWithContext(admin, "size")

        //then:
        assertThat(globalContextTags).hasSize(1)
        assertThat(globalContextTags).contains(cool)

        //and:
        assertThat(sizeTags).hasSize(2)
        assertThat(sizeTags).contains(small)
        assertThat(sizeTags).contains(tall)
    }

    @Test
    void "setTagsForEntityWithContext tags an entity under a given context when a context is specified"() {

        //when:
        taggingService.setTagsForEntityWithContext(admin, [cool], "coolness")
        taggingService.setTagsForEntityWithContext(admin, [small, tall], "size")

        //then:
        def coolnessTags = taggingService.getTagsWithContext(admin, "coolness")
        assertThat(coolnessTags).hasSize(1)
        assertThat(coolnessTags).contains(cool)

        //and:
        def sizeTags = taggingService.getTagsWithContext(admin, "size")
        assertThat(sizeTags).hasSize(2)
        assertThat(sizeTags).contains(small)
        assertThat(sizeTags).contains(tall)
    }

    @Test
    void "getTagsWithContext returns all tags for a given entity for a particular context"() {

        //given:
        tagging(cool, admin, "coolness")

        //and:
        tagging(small, admin, "size")
        tagging(tall, admin, "size")

        //when:
        def tagsWithContextSize = taggingService.getTagsWithContext(admin, "size")

        //then:
        assertThat(tagsWithContextSize).hasSize(2)
    }

    private void tagging(Tag tag, User taggable, String context) {
        def tagging = dataManager.create(Tagging)

        tagging.tag = tag
        tagging.context = context
        tagging.taggable = taggable

        dataManager.commit(tagging)
    }

    private Tag tag(String value) {
        def tag = metadata.create(Tag)

        tag.value = value

        dataManager.commit(tag)
    }

    private User loadUser(String username) {
        dataManager
                .load(User)
                .query('select e from sec$User e where e.login = :username')
                .parameter("username", username)
                .one()
    }
}