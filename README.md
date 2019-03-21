[![Build Status](https://travis-ci.com/mariodavid/cuba-component-taggable.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-taggable)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-taggable/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-taggable/_latestVersion)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

CUBA component - Taggable
======================

This application component let's you enhance your entities with a generic tagging functionality. 

## Installation

1. `taggable` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace/taggable)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.0.x            | 0.4.x          |
| 6.10.x           | 0.3.x          |
| 6.9.x            | 0.1.x - 0.2.x  |


The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-taggable/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-taggable/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.taggable`
* Artifact name: `taggable-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.taggable:taggable-global:*addon-version*")
}
```


## Using the application component


Annotate your browse screens with the `@WithTags` annotation or by implementing the `WithTagsSupport` interface.
The resulting UI looks like this:

![tags-overview](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/tags-overview.png)


### @WithTags annotation (CUBA 6 screens)

```groovy
@WithTags(listComponent = "customersTable", showTagsInList = true, showTagsAsLink = true, tagLinkOpenType = "NEW_TAB")
class CustomerBrowse extends AnnotatableAbstractLookup {
}
```


### WithTagsSupport interface (CUBA 7 screens)

```java
class CustomerBrowse extends StandardLookup<Customer> implements WithTagsSupport {

    @Inject
    private GroupTable<Customer> customersTable;

    @Inject
    private ButtonsPanel buttonsPanel;

    @Override
    Table getListComponent() {
        return customersTable;
    }

    @Override
    ButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }

}
```

### Configuration options

With that your `Customer` entity will be taggable and might look like the following:

The following options are available for the Annotation / Interface:

* `listComponent` - the list component the tagging functionality should be placed upon (normally the Table of the browse screen
* `showTagsInList` - this option will create a column in the table and renders all tags for the corresponding row
* `showTagsAsLink` - renders the tags as links, which open screen that displays all entites that are tagged with this link
* `tagLinkOpenType` - configures the open type of the tag link screen (only applicable if `showTagsAsLink` = true)
* `persistentAttribute` - if a persistent attribute in a subclass of `Tagging` is used for faster data access, it can be defined to be used here
* `tagContext` - a string that identifies the context of this tag usage


## JPA interactions with Tag entity

With the plain use of the `@WithTags` annotation, it is not directly possible to use the Tag entity within the JPA layer alongside with your entities.

One example of this restriction is the filter component to filter all entries (like Customers) for a given tag. Also programmatic joins from business entities
to `Tag` is by default not possible. The reason is that in order to do that, the JPA layer needs to know about the relationship between a `Tagging` (one entity within this application component)
 and the entity to filter for (e.g. `Customer`).


But it is possible to enable this kind of interaction with the `Tagging` entity by extending it and making the entity aware of the relationships.

### Extending Tagging Entity

In the application you have to create an Entity that extends `Tagging` and uses CUBAs `@Extends` functionality to replace the original entity with the new subclass.

```
@Extends(Tagging.class)
@Entity(name = "myApp$ExtendedTagging")
public class ExtendedTagging extends Tagging {
    private static final long serialVersionUID = 6795917365659671988L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    protected Customer customer;

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

}
```

With that it is possible to create an additional reference from the Customer entity to the Tagging entity like this:

```
@NamePattern("%s|name")
@Table(name = "MYAPP_CUSTOMER")
@Entity(name = "myApp$Customer")
public class Customer extends StandardEntity {
    private static final long serialVersionUID = 2263337300282568L;

    @Column(name = "NAME")
    protected String name;

    @OneToMany(mappedBy = "customer")
    protected List<ExtendedTagging> taggings;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ExtendedTagging> getTaggings() {
        return taggings;
    }

    public void setTaggings(List<ExtendedTagging> taggings) {
        this.taggings = taggings;
    }

}
```

### Using persistentAttribute option in @WithTags

The second step is then to define the `persistentAttribute` in the usage of the `@WithTags` annotation (CUBA 6) or `WithTagsSupport` interface (CUBA 7) like this:

```
@WithTags(
    listComponent = "customersTable",
    persistentAttribute =  "customer"
)
class CustomerBrowse extends AnnotatableAbstractLookup {
}
```

With that configuration in place, the persistent reference attribute will also be used to store the reference from the `Tagging` entity
to the customer entity. When the Tagging functionality is used for multiple different entities, each of those entities can get a different persistent
Attribute in the `ExtendedTagging`.

Now it is possible to use e.g. the filter functionality of CUBA directly, to search e.g. for all Customers that are tagged
with a particular tag.

![tags-overview-filtering](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/tags-overview-filtering.png)


![tags-overview-filter-definition](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/tags-overview-filter-definition.png)

## Scope tags with tag contexts

By default the created tags in the application are available for selection in all contexts. So if both a `Customer` and a `Order` entity
both use the Tag functionality, a new Tag that is entered in the context of the Customer browse screen, the same Tag will also be available
for selection in the context of the Order browse screen.

This global behavior might not always be desired. Therefore there is an option to set a String identifier called `tagContext` that will scope
the Tags that are available for selection for only this context.

In the example above, it is possible to scope the available tags for the customer use case to only be available within this screen:

```
@WithTags(
    listComponent = "customersTable",
    tagContext =  "customer"
)
class CustomerBrowse extends AnnotatableAbstractLookup {
}
```

The scope might not only be applicable on a per entity basis. But also for different use-cases for one Entity it is possible
to define different contexts on a per-screen basis.

## Example usage
To see this application component in action, check out this example: [cuba-example-using-taggable](https://github.com/mariodavid/cuba-example-using-taggable).



## Supported DBMS

The following databases are supported by this application component:

* HSQLDB
* PostgreSQL
* MySQL
