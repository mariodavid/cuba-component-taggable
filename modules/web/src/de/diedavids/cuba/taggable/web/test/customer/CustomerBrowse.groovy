package de.diedavids.cuba.taggable.web.test.customer

import de.balvi.cuba.declarativecontrollers.web.browse.AnnotatableAbstractLookup
import de.diedavids.cuba.taggable.web.WithTags

@WithTags(listComponent = "customersTable", showTagsInList = true, showTagsAsLink = true, tagLinkOpenType = "NEW_TAB")
class CustomerBrowse extends AnnotatableAbstractLookup {
}