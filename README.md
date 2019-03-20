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


### @WithTags annotation

```groovy
@WithTags(listComponent = "customersTable", showTagsInList = true, showTagsAsLink = true, tagLinkOpenType = "NEW_TAB")
class CustomerBrowse extends AnnotatableAbstractLookup {

}
```

With that your `Customer` entity will be taggable and might look like the following:


![tags-overview](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/tags-overview.png)

The following options are available for the Annotation:

* `listComponent` - the list component the tagging functionality should be placed upon (normally the Table of the browse screen
* `showTagsInList` - this option will create a column in the table and renders all tags for the corresponding row
* `showTagsAsLink` - renders the tags as links, which open screen that displays all entites that are tagged with this link
* `tagLinkOpenType` - configures the open type of the tag link screen (only applicable if `showTagsAsLink` = true)
* `persistentAttribute` - if a persistent attribute in a subclass of `Tagging` is used for faster data access, it can be defined to be used here
* `tagContext` - a string that identifies the context of this tag usage


### JPA interactions with Tag entity

With the plain use of the `@WithTags` annotation, it is not directly possible to use the Tag entity within the JPA layer alongside with your entities.

One example of this restriction is the filter component to filter all entries (like Customers) for a given tag. Also programmatic joins from business entities
to `Tag` is by default not possible. The reason is that in order to do that, the JPA layer needs to know about the relationship between a `Tagging` (one entity within this application component)
 and the entity to filter for (e.g. `Customer`).



![tags-overview-filtering](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/tags-overview-filtering.png)


But it is possible to enable this kind of interaction with the `Tag` entity.


![tags-overview-filter-definition](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/tags-overview-filter-definition.png)


## Example usage
To see this application component in action, check out this example: [cuba-example-using-taggable](https://github.com/mariodavid/cuba-example-using-taggable).



## Supported DBMS

The following databases are supported by this application component:

* HSQLDB
* PostgreSQL
* MySQL
