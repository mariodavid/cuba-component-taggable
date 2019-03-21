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
     *
     * @return the table
     */
    Table getListComponent();

    /**
     * the button id of the destination button
     * <p>
     * It will either picked up from existing XML definitions or created with this identifier
     *
     * @return the button identifier
     */
    default String getButtonId() {
        return "tagsBtn";
    }


    /**
     * defines the button panel that will be used for inserting the tags button
     *
     * @return the destination buttonPanel
     */
    ButtonsPanel getButtonsPanel();


    /**
     * defines the optional persistent attribute of the extended Tagging entity
     * that should be used for additionally storing the references between the Tagging entity and the usage entity
     *
     * @return the attribute name of the persistent attribute of the extended Tagging entity
     */
    default String getPersistentAttribute() {
        return null;
    }


    /**
     * defines the tag context that the tagging functionality should be scoped towards
     * It can be any string, that is treated as a identifier to differentiate between different contexts
     *
     * @return the identifier that defines the tag context
     */
    default String getTagContext() {
        return null;
    }


    /**
     * option to determine if the table (see #getListComponent()) should be enhanced by a column that shows the
     * tags as a CSV list
     *
     * @return whether the tags should be displayed in the list (default false)
     */
    default boolean showTagsInList() {
        return false;
    }

    /**
     * option to determine if the tags are rendered as links in case they are displayed in the list
     *
     * @return whether the tags should be displayed as links
     */
    default boolean showTagsAsLink() {
        return false;
    }


    /**
     * option to configure the option type of the tag link
     */
    default WindowManager.OpenType tagLinkOpenType() {
        return WindowManager.OpenType.DIALOG;
    }

    @Subscribe
    default void initTagsButton(Screen.InitEvent event) {

        Screen screen = event.getSource();
        Button button = createOrGetButton(screen);

        initButtonWithTagsFunctionality(screen, button);
    }

    default void initButtonWithTagsFunctionality(Screen screen, Button button) {

        WithTagsSupportExecution withTagsBean = getBeanLocator(screen).get(WithTagsSupportExecution.class);
        Messages messages = getBeanLocator(screen).get(Messages.class);

        ListAction tagsAction = new ItemTrackingAction(getListComponent(), "tagsAction")
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


    default Button createOrGetButton(Screen screen) {
        BeanLocator beanLocator = getBeanLocator(screen);
        ButtonsPanelHelper buttonsPanelHelper = beanLocator.get(ButtonsPanelHelper.NAME);

        return buttonsPanelHelper.createButton(getButtonId(), getButtonsPanel(), Collections.emptyList());
    }


    @Subscribe
    default void initTagsTableColumn(Screen.InitEvent event) {

        if (showTagsInList()) {
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
    }


    default BeanLocator getBeanLocator(Screen screen) {
        return Extensions.getBeanLocator(screen);
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

        } else {
            Label tagComponent = uiComponents.create(Label.NAME);
            tagComponent.setValue(tag.getValue());
            tagComponent.setIcon(ICON_KEY);
            return tagComponent;

        }
    }


}