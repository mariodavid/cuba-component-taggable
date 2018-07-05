package de.diedavids.cuba.taggable.web.action

import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.gui.components.Component
import com.haulmont.cuba.gui.components.ListComponent
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction

class TableWithTagsAction extends ItemTrackingAction {


    WithTagsBean withTagsBean = AppBeans.<WithTagsBean> get(WithTagsBean)
    String persistentAttribute = null

    @SuppressWarnings('ThisReferenceEscapesConstructor')
    TableWithTagsAction(ListComponent listComponent, String persistentAttribute) {
        super(WithTagsBean.ACTION_ID)
        target = listComponent
        withTagsBean.setIcon(this)
        withTagsBean.setCaption(this)
        this.persistentAttribute = persistentAttribute
    }

    @Override
    void actionPerform(Component component) {
        withTagsBean.openTagAssignment(target, persistentAttribute)
    }
}
