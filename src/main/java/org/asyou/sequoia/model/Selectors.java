package org.asyou.sequoia.model;

import org.bson.BSONObject;

import java.util.List;

import static org.asyou.sequoia.model.Commons.*;

/**
 * Created by steven on 17/10/10.
 */
public final class Selectors {
    public static BSONObject include(int value, String fieldName){
        return insideOpt(fieldName, "$include", value);
    }

    public static BSONObject include(int value, String... fieldNames){
        return combine(value,fieldNames);
    }

    public static <T> BSONObject $default(String fieldName, T value){
        return insideOpt(fieldName, "$default", value);
    }

    public static <T> BSONObject elemMatch(String fieldName, T value){
        return insideOpt(fieldName, "$elemMatch", value);
    }

    public static <T> BSONObject elemMatchOne(String fieldName, T value){
        return insideOpt(fieldName, "$elemMatchOne", value);
    }
}
