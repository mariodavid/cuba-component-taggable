package de.diedavids.cuba.taggable.web.example.customer;

import com.haulmont.bali.util.ParamsMap;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.icons.Icons;
import com.haulmont.cuba.gui.icons.IconsImpl;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import de.diedavids.cuba.taggable.entity.Tag;
import de.diedavids.cuba.taggable.entity.example.Customer;
import de.diedavids.cuba.taggable.service.TaggingService;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class CustomerBrowse extends AbstractLookup {

    @Inject
    protected GroupTable<Customer> customersTable;


    @Inject
    protected GroupDatasource<Customer, UUID> customersDs;
    @Inject
    protected ComponentsFactory componentsFactory;

    @Inject
    protected TaggingService taggingService;

    @Inject
    Icons icons;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);

        customersTable.addGeneratedColumn("tags", new Table.ColumnGenerator<Customer>() {
            @Override
            public Component generateCell(Customer entity) {

                HBoxLayout layout = (HBoxLayout) componentsFactory.createComponent(HBoxLayout.NAME);
                layout.setSpacing(true);

                Collection<Tag> tags = taggingService.getTags(entity);


                for (Tag tag : tags) {
                    Label tagLabel = (Label) componentsFactory.createComponent(Label.NAME);
                    tagLabel.setValue(tag.getValue());
                    tagLabel.setIcon("font-icon:TAG");


                    layout.add(tagLabel);
                }
                return layout;
            }
        });
    }

    public void tag() {

        AbstractWindow assignTags = openWindow("ddct$TagAssignment", WindowManager.OpenType.DIALOG, ParamsMap.of("taggable", customersTable.getSingleSelected()));
        assignTags.addCloseWithCommitListener(() -> customersDs.refresh());
    }
}