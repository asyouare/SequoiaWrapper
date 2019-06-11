package org.asyou.sequoia.model;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;

/**
 * Created by steven on 17/10/10.
 */
public final class Updates {

    public static <T>BSONObject inc(String fieldName, T value){
        return Commons.outsideOpt("$inc",fieldName, value);
    }

    public static <T>BSONObject inc(BSONObject value){
        return Commons.createBSONObject("$inc",value);
    }

    public static <T>BSONObject set(String fieldName, T value){
        return Commons.outsideOpt("$set",fieldName, value);
    }

    public static <T>BSONObject set(BSONObject value){
        return Commons.createBSONObject("$set",value);
    }

    public static <T>BSONObject unset(String fieldName, T value){
        return Commons.outsideOpt("$unset",fieldName, value);
    }

    public static <T>BSONObject unset(BSONObject value){
        return Commons.createBSONObject("$unset",value);
    }

    public static <T>BSONObject addtoset(String fieldName, T... values){
        return Commons.outsideOpt("$addtoset",fieldName, values);
    }

    public static BSONObject addtoset(BSONObject value){
        return Commons.createBSONObject("$addtoset",value);
    }

    public static <T>BSONObject pop(String fieldName, T value){
        return Commons.outsideOpt("$pop",fieldName, value);
    }

    public static BSONObject pop(BSONObject value){
        return Commons.createBSONObject("$pop",value);
    }

    public static <T>BSONObject pull(String fieldName, T value){
        return Commons.outsideOpt("$pull",fieldName, value);
    }

    public static BSONObject pull(BSONObject value){
        return Commons.createBSONObject("$pull",value);
    }

    public static <T>BSONObject pull_all(String fieldName, T... value){
        return Commons.outsideOpt("$pull_all",fieldName, value);
    }

    public static BSONObject pull_all(BSONObject value){
        return Commons.createBSONObject("$pull_all",value);
    }

    public static <T>BSONObject pull_by(String fieldName, T value){
        return Commons.outsideOpt("$pull_by",fieldName, value);
    }

    public static BSONObject pull_by(BSONObject value){
        return Commons.createBSONObject("$pull_by",value);
    }

    public static <T>BSONObject pull_all_by(String fieldName, T... value){
        return Commons.outsideOpt("$pull_all_by",fieldName, value);
    }

    public static BSONObject pull_all_by(BSONObject value){
        return Commons.createBSONObject("$pull_all_by",value);
    }

    public static <T>BSONObject push(String fieldName, T value){
        return Commons.outsideOpt("$push",fieldName, value);
    }

    public static BSONObject push(BSONObject value){
        return Commons.createBSONObject("$push",value);
    }

    public static <T>BSONObject push_all(String fieldName, T... value){
        return Commons.outsideOpt("$push_all",fieldName, value);
    }

    public static BSONObject push_all(BSONObject value){
        return Commons.createBSONObject("$push_all",value);
    }

    public static <T>BSONObject replace(String fieldName, T value){
        return Commons.outsideOpt("$replace",fieldName, value);
    }

    public static BSONObject replace(BSONObject value){
        return Commons.createBSONObject("$replace",value);
    }
}
