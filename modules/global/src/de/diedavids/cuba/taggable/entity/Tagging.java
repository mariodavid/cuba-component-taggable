package de.diedavids.cuba.taggable.entity;

import javax.persistence.*;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.entitysoftreference.EntitySoftReferenceConverter;

import javax.validation.constraints.NotNull;
import com.haulmont.cuba.core.entity.StandardEntity;

@Table(name = "DDCT_TAGGING", indexes = {
    @Index(name = "IDX_DDCT_TAGGING_ON_TAGGABLE", columnList = "TAGGABLE")
})
@Entity(name = "ddct$Tagging")
public class Tagging extends StandardEntity {
    private static final long serialVersionUID = 6144965403733204673L;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "TAG_ID")
    protected Tag tag;

    @NotNull
    @MetaProperty(datatype = "EntitySoftReference", mandatory = true)
    @Convert(converter = EntitySoftReferenceConverter.class)
    @Column(name = "TAGGABLE", nullable = false)
    protected com.haulmont.cuba.core.entity.Entity taggable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TAGGER_ID")
    protected User tagger;

    @Column(name = "CONTEXT")
    protected String context;

    public com.haulmont.cuba.core.entity.Entity getTaggable() {
        return taggable;
    }

    public void setTaggable(com.haulmont.cuba.core.entity.Entity taggable) {
        this.taggable = taggable;
    }





    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTagger(User tagger) {
        this.tagger = tagger;
    }

    public User getTagger() {
        return tagger;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContext() {
        return context;
    }


}