package de.diedavids.cuba.taggable.web.action;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.ScreenBuilders;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.AbstractWindow;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.components.Window;
import com.haulmont.cuba.gui.model.ScreenData;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Map;

@Component(WithTagsSupportExecution.NAME)
public class WithTagsSupportExecutionBean implements WithTagsSupportExecution {

  private static final String SCREEN_ID_TAG_ASSIGNMENT = "ddct$TagAssignment";

  @Override
  public void openTagAssignment(ListComponent target, String persistentAttribute, String tagContext, ScreenData screenData) {
    AbstractWindow assignTagsWindow = target.getFrame().openWindow(
            SCREEN_ID_TAG_ASSIGNMENT,
            WindowManager.OpenType.DIALOG,
            getScreenParams(target.getSingleSelected(), persistentAttribute, tagContext)
    );

    assignTagsWindow.addCloseWithCommitListener(screenData::loadAll);
  }


  private Map<String, Object> getScreenParams(Entity item, String persistentAttribute, String tagContext) {
    Map<String, Object> result = ParamsMap.of("taggable", item);

    if (!StringUtils.isEmpty(persistentAttribute)) {
      result.put("persistentAttribute", persistentAttribute);
    }

    if (!StringUtils.isEmpty(tagContext)) {
      result.put("tagContext", tagContext);
    }

    return result;
  }
}
