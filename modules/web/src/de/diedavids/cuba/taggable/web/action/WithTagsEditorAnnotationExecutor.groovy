package de.diedavids.cuba.taggable.web.action

import com.haulmont.cuba.gui.components.Button
import com.haulmont.cuba.gui.components.Window
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorAnnotationExecutor
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper
import de.diedavids.cuba.taggable.web.WithTags
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import javax.inject.Inject
import java.lang.annotation.Annotation

@CompileStatic
@Component('ddct$WithTagsEditorAnnotationExecutor')
class WithTagsEditorAnnotationExecutor implements EditorAnnotationExecutor<WithTags> {


    @Inject
    ButtonsPanelHelper buttonsPanelHelper


    @SuppressWarnings('Instanceof')
    boolean supports(Annotation annotation) {
        annotation instanceof WithTags
    }

    @Override
    void init(WithTags annotation, Window.Editor editor, Map<String, Object> params) {
        Button button = buttonsPanelHelper.getOrCreateButton(editor, annotation.buttonId(), annotation.buttonsPanel())
        button.action = new EditorWithTagsAction(editor)
    }

    @Override
    void postInit(WithTags withTags, Window.Editor editor) {

    }
}