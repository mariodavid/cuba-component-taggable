package de.diedavids.cuba.taggable.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import java.util.List;
import javax.persistence.OneToMany;

@NamePattern("%s|value")
@Table(name = "DDCT_TAG")
@Entity(name = "ddct$Tag")
public class Tag extends StandardEntity {
    private static final long serialVersionUID = 2701320502267744545L;

    @NotNull
    @Column(name = "VALUE_", nullable = false)
    protected String value;

    @OneToMany(mappedBy = "tag")
    protected List<Tagging> taggings;

    public void setTaggings(List<Tagging> taggings) {
        this.taggings = taggings;
    }

    public List<Tagging> getTaggings() {
        return taggings;
    }


    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}