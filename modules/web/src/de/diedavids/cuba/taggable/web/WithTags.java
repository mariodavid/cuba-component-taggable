package de.diedavids.cuba.taggable.web;

import com.haulmont.cuba.gui.WindowManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WithTags {
    String listComponent() default "";
    String persistentAttribute() default "";
    String tagContext() default "";
    String buttonId() default "tagsBtn";
    String buttonsPanel() default "buttonsPanel";
    boolean showTagsInList() default false;
    boolean showTagsAsLink() default false;
    String tagLinkOpenType() default "DIALOG";
}