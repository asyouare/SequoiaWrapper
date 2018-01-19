package org.asyou.sequoia.model;

import org.bson.BSONObject;

import static org.asyou.sequoia.model.Commons.*;

/**
 * Created by steven on 17/10/10.
 */
public final class Functions {

    public static BSONObject abs(String fieldName){
        return insideOpt(fieldName, "$abs", 1);
    }

    public static BSONObject ceiling(String fieldName){
        return insideOpt(fieldName, "$ceiling", 1);
    }

    public static BSONObject floor(String fieldName){
        return insideOpt(fieldName, "$floor", 1);
    }

    public static BSONObject mod(String fieldName, int value){
        return insideOpt(fieldName, "$mod", value);
    }

    public static BSONObject add(String fieldName, int value){
        return insideOpt(fieldName, "$add", value);
    }

    public static BSONObject subtract(String fieldName, int value){
        return insideOpt(fieldName, "$subtract", value);
    }

    public static BSONObject multiply(String fieldName, int value){
        return insideOpt(fieldName, "$multiply", value);
    }

    public static BSONObject divide(String fieldName, int value){
        return insideOpt(fieldName, "$divide", value);
    }

    public static BSONObject substr(String fieldName, int length){
        return substr(fieldName, 0, length);
    }

    public static BSONObject substr(String fieldName, int beginIndex, int length){
        return insideOpt(fieldName, "$substr", new int[]{beginIndex, length});
    }

    public static BSONObject strlen(String fieldName){
        return insideOpt(fieldName, "$strlen", 1);
    }

    public static BSONObject lower(String fieldName){
        return insideOpt(fieldName, "$lower", 1);
    }

    public static BSONObject upper(String fieldName){
        return insideOpt(fieldName, "$upper", 1);
    }

    public static BSONObject ltrim(String fieldName){
        return insideOpt(fieldName, "$ltrim", 1);
    }

    public static BSONObject rtrim(String fieldName){
        return insideOpt(fieldName, "$rtrim", 1);
    }

    public static BSONObject trim(String fieldName){
        return insideOpt(fieldName, "$trim", 1);
    }

    public static BSONObject cast(String fieldName, String targetTypeName){
        return insideOpt(fieldName, "$cast", targetTypeName);
    }

    public static BSONObject size(String fieldName){
        return insideOpt(fieldName, "$size", 1);
    }

    public static BSONObject type(String fieldName, int value){
        return insideOpt(fieldName, "$type", value);
    }

    public static BSONObject slice(String fieldName, int length){
        return slice(fieldName, 0, length);
    }

    public static BSONObject slice(String fieldName, int beginIndex, int length){
        return insideOpt(fieldName, "$slice", new int[]{beginIndex,length});
    }
}
