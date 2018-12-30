package de.diedavids.cuba.taggable.web.action

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
@Component('ddct$WithTagsBean')
class WithTagsBean {

  public static final String ACTION_ID = 'addTags'
  private static final String CAPTION_MSG_KEY = 'actions.tags'
  private static final String ICON_KEY = 'font-icon:TAGS'

  @Inject
  Messages messages
  private static final String SCREEN_ID_TAG_ASSIGNMENT = 'ddct$TagAssignment'

  void setCaption(Action action) {
    action.setCaption(messages.getMainMessage(CAPTION_MSG_KEY))
  }

  void setIcon(Action action) {
    action.setIcon(ICON_KEY)
  }

  void openTagAssignment(ListComponent target, String persistentAttribute) {
    AbstractWindow assignTagsWindow = (AbstractWindow) target.frame.openWindow(
        SCREEN_ID_TAG_ASSIGNMENT,
        WindowManager.OpenType.DIALOG,
        getScreenParams(target.singleSelected, persistentAttribute)
    )

    assignTagsWindow.addCloseWithCommitListener {
      target.datasource.refresh()
    }
  }

  void openTagAssignment(Window.Editor editor, String persistentAttribute) {
    editor.frame.openWindow(
        SCREEN_ID_TAG_ASSIGNMENT,
        WindowManager.OpenType.DIALOG,
        getScreenParams(editor.item, persistentAttribute)
    )
  }

  private Map<String, Object> getScreenParams(Entity item, String persistentAttribute) {
    def result = [:]

    result['taggable'] = item
    if (persistentAttribute) {
      result['persistentAttribute'] = persistentAttribute
    }

    result
  }
}
