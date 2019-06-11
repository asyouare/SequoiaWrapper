package org.asyou.sequoia.model;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.*;

/**
 * basic operate for create BSONObject
 * Created by steven on 17/10/10.
 */
public class Commons {

    public static <T> BSONObject insideOpt(String fieldName, String operatorName, T value){
        return new BasicBSONObject(fieldName,new BasicBSONObject(operatorName,value));
    }

    public static <T> BSONObject outsideOpt(String operatorName, String fieldName, T value){
        return new BasicBSONObject(operatorName,new BasicBSONObject(fieldName,value));
    }

    public static <T> BSONObject createBSONObject(){
        return new BasicBSONObject();
    }

    public static <T> BSONObject createBSONObject(String fieldName, T value){
        return new BasicBSONObject(fieldName, value);
    }

    public static BSONObject combine(BSONObject... bsonObjects){
        return combine(Arrays.asList(bsonObjects));
    }

    public static BSONObject combine(List<BSONObject> bsonObjects){
        BSONObject bson = new BasicBSONObject();
        for (BSONObject item : bsonObjects) {
            for (String key : item.keySet()) {
                bson.put(key, item.get(key));
            }
        }
        return bson;
    }

    public static BSONObject combine(Object value, String... fieldNames){
        return combine(value, Arrays.asList(fieldNames));
    }

    public static BSONObject combine(Object value, List<String> fieldNames){
        BSONObject bson = new BasicBSONObject();
        Iterator names = fieldNames.iterator();
        while(names.hasNext()) {
            String fieldName = (String)names.next();
            bson.put(fieldName,value);
        }
        return bson;
    }

}
