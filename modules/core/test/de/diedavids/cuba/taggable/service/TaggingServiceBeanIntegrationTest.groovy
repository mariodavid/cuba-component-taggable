package de.diedavids.cuba.taggable.service

import com.haulmont.cuba.core.Persistence
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.taggable.DdctTestContainer
import de.diedavids.cuba.taggable.entity.Tag
import org.junit.Before
import org.junit.ClassRule
import org.junit.Test


class TaggingServiceBeanIntegrationTest {

    @ClassRule
    public static DdctTestContainer cont = DdctTestContainer.Common.INSTANCE;

    private Metadata metadata;
    private Persistence persistence;
    private DataManager dataManager
    private TaggingService taggingService

    @Before
    void before() {
        metadata = cont.metadata()
        persistence = cont.persistence()
        dataManager = AppBeans.get(DataManager)

        taggingService =  AppBeans.get(TaggingService)
    }


    @Test
    void "tagEntity tags an entity"() {

        //given:
        def admin = dataManager.load(User).query("select e from sec\$User e where e.login = 'admin'").one()

        def coolTag = metadata.create(Tag)
        coolTag.value = "cool"
        def persistedCoolTag = dataManager.commit(coolTag)

        //when:
        taggingService.tagEntity(admin, [persistedCoolTag])

        //then:
        taggingService.getTags(admin).size() == 1
        taggingService.getTags(admin).contains(persistedCoolTag)
    }

}