package de.diedavids.cuba.taggable.web;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.BeanLocator;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction;
import com.haulmont.cuba.gui.components.actions.ListAction;
import com.haulmont.cuba.gui.screen.Extensions;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.UiControllerUtils;
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.service.TaggingService;
import de.diedavids.cuba.taggable.web.action.WithTagsSupportExecution;

import java.util.Collection;
import java.util.Collections;

public interface WithTagsSupport {





    String BUTTON_MSG_KEY = "actions.tags";
    String COLUMN_MSG_KEY = "column.tags";
    String ICON_KEY = "font-icon:TAGS";

    /**
     * defines the table component that will be used as a basis for the tag functionality
     * @return the table
     */
    Table getListComponent();

    /**
     * the button id of the destination button
     *
     * It will either picked up from existing XML definitions or created with this identifier
     * @return the button identifier
     */
    default String getButtonId() {
        return "tagsBtn";
    }


    /**
     * defines the button panel that will be used for inserting the tags button
     * @return the destination buttonPanel
     */
    ButtonsPanel getButtonsPanel();


    default String getPersistentAttribute() {
        return null;
    }

    default String getTagContext() {
        return null;
    }



    default boolean showTagsAsLink() { return false; }

    default WindowManager.OpenType tagLinkOpenType() { return WindowManager.OpenType.DIALOG; }

    @Subscribe
    default void initTagsButton(Screen.InitEvent event) {

        Screen screen = event.getSource();
        Button button = createOrGetButton(screen);

        initButtonWithTagsFunctionality(screen, button);
    }

    default Button createOrGetButton(Screen screen) {
        BeanLocator beanLocator = getBeanLocator(screen);
        ButtonsPanelHelper buttonsPanelHelper = beanLocator.get(ButtonsPanelHelper.NAME);

        return buttonsPanelHelper.createButton(getButtonId(), getButtonsPanel(), Collections.emptyList());
    }

    default BeanLocator getBeanLocator(Screen screen) {
        return Extensions.getBeanLocator(screen);
    }

    default void initButtonWithTagsFunctionality(Screen screen, Button button) {

        WithTagsSupportExecution withTagsBean = getBeanLocator(screen).get(WithTagsSupportExecution.class);
        Messages messages = getBeanLocator(screen).get(Messages.class);

        ListAction tagsAction = new ItemTrackingAction("tagsAction")
                .withPrimary(true)
                .withIcon(ICON_KEY)
                .withCaption(messages.getMainMessage(BUTTON_MSG_KEY))
                .withHandler(e -> withTagsBean.openTagAssignment(
                        getListComponent(),
                        getPersistentAttribute(),
                        getTagContext(),
                        UiControllerUtils.getScreenData(screen)
                ));

        button.setAction(tagsAction);

    }




    @Subscribe
    default void initTagsTableColumn(Screen.InitEvent event) {

        Screen screen = event.getSource();

        BeanLocator beanLocator = Extensions.getBeanLocator(screen);

        TaggingService taggingService = beanLocator.get(TaggingService.class);
        UiComponents uiComponents = beanLocator.get(UiComponents.class);
        Messages messages = beanLocator.get(Messages.class);

        getListComponent().addGeneratedColumn(messages.getMainMessage(COLUMN_MSG_KEY), (Table.ColumnGenerator<Entity>) entity -> {
            Collection<Tag> tags = taggingService.getTagsWithContext(entity, getTagContext());
            ComponentContainer layout = createContainerComponentForTags(uiComponents);

            for (Tag tag : tags) {
                layout.add(createComponentForTag(uiComponents, tag, getListComponent().getFrame()));
            }
            return layout;
        });
    }


    default ComponentContainer createContainerComponentForTags(UiComponents uiComponents) {
        HBoxLayout layout = uiComponents.create(HBoxLayout.NAME);
        layout.setSpacing(true);
        return layout;
    }

    default Component createComponentForTag(UiComponents uiComponents, Tag tag, Frame frame) {
        if (showTagsAsLink()) {
            LinkButton tagComponent = uiComponents.create(LinkButton.NAME);
            tagComponent.setCaption(tag.getValue());
            tagComponent.setIcon(ICON_KEY);
            tagComponent.setAction(
                new BaseAction("openTag")
                        .withHandler(e -> {
                            frame.openEditor(tag, tagLinkOpenType(), ParamsMap.of("tagLinkOpenType", tagLinkOpenType()));
                        })
            );
            return tagComponent;

        }
        else {
            Label tagComponent = uiComponents.create(Label.NAME);
            tagComponent.setValue(tag.getValue());
            tagComponent.setIcon(ICON_KEY);
            return tagComponent;

        }
    }


}