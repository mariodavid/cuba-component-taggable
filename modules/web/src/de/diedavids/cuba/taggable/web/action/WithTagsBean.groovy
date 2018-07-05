package de.diedavids.cuba.taggable.web.action

import com.haulmont.bali.util.ParamsMap
import com.haulmont.chile.core.model.MetaClass
import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.gui.WindowManager
import com.haulmont.cuba.gui.components.AbstractWindow
import com.haulmont.cuba.gui.components.Action
import com.haulmont.cuba.gui.components.ListComponent
import com.haulmont.cuba.gui.components.Window
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import javax.inject.Inject

@CompileStatic
@Component('ddcdi$WithImportBean')
class WithTagsBean {

    public static final String ACTION_ID = 'addTags'
    private static final String IMPORT_CAPTION_MSG_KEY = 'actions.tags'
    private static final String IMPORT_ICON_KEY = 'font-icon:TAGS'

    @Inject
    Messages messages


    void setCaption(Action action) {
        action.setCaption(messages.getMainMessage(IMPORT_CAPTION_MSG_KEY))
    }

    void setIcon(Action action) {
        action.setIcon(IMPORT_ICON_KEY)
    }


    void openTagAssignment(ListComponent target, String persistentAttribute) {
        AbstractWindow myWindow = (AbstractWindow) target.frame.openWindow(
                'ddct$TagAssignment',
                WindowManager.OpenType.DIALOG,
                getScreenParams(target.singleSelected, persistentAttribute)
        );

        myWindow.addCloseWithCommitListener {
            target.datasource.refresh()
        }
    }
    void openTagAssignment(Window.Editor editor, String persistentAttribute) {
        AbstractWindow myWindow = (AbstractWindow) editor.frame.openWindow(
                'ddct$TagAssignment',
                WindowManager.OpenType.DIALOG,
                getScreenParams(editor.item, persistentAttribute)
        );

    }

    private Map<String, Object> getScreenParams(Entity item, String persistentAttribute) {
        def result = [:]

        result["taggable"] = item
        if (persistentAttribute) {
            result["persistentAttribute"] = persistentAttribute
        }

        result
    }

}
