package de.diedavids.cuba.taggable.entity.example;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s|name")
@Table(name = "DDCT_CUSTOMER")
@Entity(name = "ddct$Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = -5892137580623994867L;

    @Column(name = "NAME")
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


}