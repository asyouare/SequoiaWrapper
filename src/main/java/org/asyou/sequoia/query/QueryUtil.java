package org.asyou.sequoia.query;

import org.apache.commons.lang3.StringUtils;
import org.asyou.sequoia.annotation.CollectionName;
import org.asyou.sequoia.annotation.CollectionSpaceName;
import org.asyou.sequoia.type.CollectionNameType;

/**
 * Created by steven on 17/9/20.
 */

public class QueryUtil {
    //优先使用collectionName, 其次Annotation, 再次ClassName
    public static CollectionNameType getCollectionName(Class<?> clazz, CollectionNameType collectionNameType){
        if (null != collectionNameType && collectionNameType.isNotBlank()){
            return collectionNameType;
        }else {
            if (clazz != null){
                CollectionSpaceName collectionName = clazz.getAnnotation(CollectionSpaceName.class);
                if (collectionName != null){
                    CollectionNameType type = new CollectionNameType(collectionName.spaceName(),collectionName.collectionName());
                    if (type.isNotBlank()) {
                        return type;
                    }
                }
            }
        }
        return null;
    }

    public static String getCollectionName(Class<?> clazz, String collectionName){
        String cn = null;
        if (StringUtils.isNotBlank(collectionName)){
            cn = collectionName;
        }else {
            if (clazz != null) {
                CollectionName c = clazz.getAnnotation(CollectionName.class);
                if (c == null) {
                    throw new IllegalArgumentException("CollectionName not found");
                }
                if (StringUtils.isNotBlank(c.name())) {
                    cn = c.name();
                } else {
                    cn = clazz.getSimpleName();
                }
            }
        }
        return cn;
    }
}
