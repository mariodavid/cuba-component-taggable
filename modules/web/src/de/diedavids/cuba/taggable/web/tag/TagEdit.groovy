package de.diedavids.cuba.taggable.web.tag

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.gui.WindowManager
import com.haulmont.cuba.gui.WindowManager.OpenType
import com.haulmont.cuba.gui.WindowParam
import com.haulmont.cuba.gui.components.AbstractEditor
import com.haulmont.cuba.gui.data.impl.CustomValueCollectionDatasource
import de.diedavids.cuba.taggable.entity.Tag

import javax.inject.Inject

class TagEdit extends AbstractEditor<Tag> {

    @Inject
    CustomValueCollectionDatasource entitiesWithTagDs

    @WindowParam(required = true)
    OpenType tagLinkOpenType

    @Override
    protected void postInit() {
        super.postInit()
        caption = formatMessage("editorCaption", item.value)

        entitiesWithTagDs.refresh([tag: item])

    }

    void openEntity(Entity item, String columnId) {
        openEditor(item.getValue("entity") as Entity, tagLinkOpenType)
    }
}