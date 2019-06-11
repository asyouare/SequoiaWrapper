package org.asyou.sequoia.annotation;

import java.lang.annotation.*;

/**
 * Created by steven on 17/9/19.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CollectionSpaceName {
    String spaceName();
    String collectionName() default "";
}