package org.asyou.sequoia.model;

import org.bson.BasicBSONObject;

public class Accumulators {
    public static BSONField sum(String displayFieldName, String targetFieldName) {
        return accumulator("$sum", displayFieldName, targetFieldName);
    }

    public static BSONField avg(String displayFieldName, String targetFieldName) {
        return accumulator("$avg", displayFieldName, targetFieldName);
    }

    public static BSONField first(String displayFieldName, String targetFieldName) {
        return accumulator("$first", displayFieldName, targetFieldName);
    }

    public static BSONField last(String displayFieldName, String targetFieldName) {
        return accumulator("$last", displayFieldName, targetFieldName);
    }

    public static BSONField max(String displayFieldName, String targetFieldName) {
        return accumulator("$max", displayFieldName, targetFieldName);
    }

    public static BSONField min(String displayFieldName, String targetFieldName) {
        return accumulator("$min", displayFieldName, targetFieldName);
    }

    public static BSONField push(String displayFieldName, String targetFieldName) {
        return accumulator("$push", displayFieldName, targetFieldName);
    }

    public static BSONField addToSet(String displayFieldName, String targetFieldName) {
        return accumulator("$addtoset", displayFieldName, targetFieldName);
    }

    public static BSONField count(String displayFieldName, String targetFieldName) {
        return accumulator("$count", displayFieldName, targetFieldName);
    }

    public static BSONField accumulator(String name, String displayFieldName, String targetFieldName) {
        return new BSONField(displayFieldName, new BasicBSONObject(name, targetFieldName));
    }

    private Accumulators() {
    }
}
