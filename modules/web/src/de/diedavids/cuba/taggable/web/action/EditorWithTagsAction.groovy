package de.diedavids.cuba.taggable.web.action

import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.gui.WindowManager
import com.haulmont.cuba.gui.components.AbstractAction
import com.haulmont.cuba.gui.components.Action
import com.haulmont.cuba.gui.components.Component
import com.haulmont.cuba.gui.components.Window
import groovy.transform.CompileStatic

@CompileStatic
class EditorWithTagsAction extends AbstractAction implements Action.HasOpenType {

    WithTagsBean withTagsBean = AppBeans.<WithTagsBean> get(WithTagsBean)
    String persistentAttribute = null
    WindowManager.OpenType openType

    protected Window.Editor editor

    EditorWithTagsAction(Window.Editor editor) {
        this(WithTagsBean.ACTION_ID, editor)
    }

    EditorWithTagsAction(String id, Window.Editor editor) {
        super(id)
        this.editor = editor
        withTagsBean.setIcon(this)
        withTagsBean.setCaption(this)
        this.persistentAttribute = persistentAttribute
    }

    @Override
    void actionPerform(Component component) {
        withTagsBean.openTagAssignment(editor, persistentAttribute)
    }
}
