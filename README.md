[![Build Status](https://travis-ci.com/mariodavid/cuba-component-taggable.svg?branch=master)](https://travis-ci.com/mariodavid/cuba-component-taggable)
[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

CUBA component - Taggable
======================

This application component let's you enhance your entities with a generic tagging functionality. 

## Installation

1. taggable is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 6.9.x            | 0.1.x          |


The latest version is: <<NOT_AVAILABLE :)>>

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


        @WithTags(listComponent = "customersTable", showTagsInList = true, showTagsAsLink = true, tagLinkOpenType = "NEW_TAB")
        class CustomerBrowse extends AnnotatableAbstractLookup {
        
        }
        

With that your `Customer` entity will be taggable and might look like the following:


![customer-browse-with-tags](https://github.com/mariodavid/cuba-component-taggable/blob/master/img/1-customer-browse-with-tags.png)


### Example usage
To see this application component in action, check out this example: [cuba-example-using-taggable](https://github.com/mariodavid/cuba-example-using-taggable).