package de.diedavids.cuba.taggable.web.action

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.gui.WindowManager
import com.haulmont.cuba.gui.components.*
import com.haulmont.cuba.gui.components.actions.BaseAction
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper
import de.diedavids.cuba.taggable.entity.Tag
import de.diedavids.cuba.taggable.service.TaggingService
import de.diedavids.cuba.taggable.web.WithTags

import javax.inject.Inject
import java.lang.annotation.Annotation

@org.springframework.stereotype.Component('ddct$WithTagsBrowseAnnotationExecutor')
class WithTagsBrowseAnnotationExecutor implements BrowseAnnotationExecutor<WithTags> {


    private static final String CAPTION_MSG_KEY = 'column.tags'
    private static final String ICON_KEY = 'font-icon:TAG'

    static final List<String> PRE_BUTTONS = [
            'createBtn',
            'editBtn',
            'removeBtn',
            'refreshBtn',
            'excelBtn',
            'reportBtn',
    ].asImmutable()


    @Inject
    ButtonsPanelHelper buttonsPanelHelper

    @Inject
    protected ComponentsFactory componentsFactory

    @Inject
    protected TaggingService taggingService


    @Inject
    protected Messages messages



    @SuppressWarnings('Instanceof')
    boolean supports(Annotation annotation) {
        annotation instanceof WithTags
    }

    @Override
    void init(WithTags annotation, Window.Lookup browse, Map<String, Object> params) {
        ListComponent listComponent = getListComponent(browse, annotation)

        def action = new TableWithTagsAction(listComponent, annotation.persistentAttribute())
        listComponent.addAction(action)
        if (annotation.buttonsPanel()) {
            ButtonsPanel buttonsPanel = browse.getComponent(annotation.buttonsPanel()) as ButtonsPanel
            Button destinationBtn = buttonsPanelHelper.createButton(annotation.buttonId(), buttonsPanel, PRE_BUTTONS)
            destinationBtn.action = action
        }
    }

    private ListComponent getListComponent(Window.Lookup browse, WithTags annotation) {
        browse.getComponent(annotation.listComponent()) as ListComponent
    }

    @Override
    void ready(WithTags annotation, Window.Lookup browse, Map<String, Object> params) {
        if (annotation.showTagsInList()) {
            Table table = getListComponent(browse, annotation) as Table
            addTagsColumnToTable(annotation, table)
        }
    }

    protected void addTagsColumnToTable(WithTags annotation, Table table) {
        table.addGeneratedColumn(messages.getMainMessage(CAPTION_MSG_KEY), new Table.ColumnGenerator<Entity>() {
            @Override
            Component generateCell(Entity entity) {
                Collection<Tag> tags = taggingService.getTags(entity)
                Component.Container layout = createContainerComponentForTags()
                for (Tag tag : tags) {
                    layout.add(createComponentForTag(annotation, tag, table.frame))
                }
                layout
            }

        })
    }

    protected Component.Container createContainerComponentForTags() {
        HBoxLayout layout = (HBoxLayout) componentsFactory.createComponent(HBoxLayout.NAME)
        layout.setSpacing(true)
        layout
    }

    protected Component createComponentForTag(WithTags annotation,Tag tag, Frame frame) {
        if (annotation.showTagsAsLink()) {
            LinkButton tagComponent = (LinkButton) componentsFactory.createComponent(LinkButton.NAME)
            tagComponent.caption = tag.value
            tagComponent.icon = ICON_KEY
            tagComponent.action = new BaseAction('openTag') {
                @Override
                void actionPerform(Component component) {
                    def openType = WindowManager.OpenType.valueOf(annotation.tagLinkOpenType())
                    frame.openEditor(tag, openType, [tagLinkOpenType: openType] as Map<String, Object>)
                }
            }
            tagComponent
            
        }
        else {
            Label tagComponent = (Label) componentsFactory.createComponent(Label.NAME)
            tagComponent.value = tag.value
            tagComponent.icon = ICON_KEY
            tagComponent
            
        }
    }
}