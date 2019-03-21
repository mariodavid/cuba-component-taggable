package de.diedavids.cuba.taggable.web.action;

import com.haulmont.cuba.gui.components.ListComponent;
import com.haulmont.cuba.gui.model.ScreenData;
import com.haulmont.cuba.gui.screen.Screen;

public interface WithTagsSupportExecution {

    String NAME = "ddct_WithTagsSupportExecution";

    void openTagAssignment(ListComponent target, String persistentAttribute, String tagContext, ScreenData screenData);
}
