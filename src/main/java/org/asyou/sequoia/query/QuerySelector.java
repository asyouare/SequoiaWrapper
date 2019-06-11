package org.asyou.sequoia.query;

import org.bson.BSONObject;

public class QuerySelector extends AQueryBSONObject implements IQuery {
    public static QuerySelector create(){
        return new QuerySelector();
    }

    public static QuerySelector create(String jsonQuery){
        return new QuerySelector(jsonQuery);
    }

    public static <T> QuerySelector create(T modelQuery){
        return new QuerySelector(modelQuery);
    }

    public static QuerySelector create(BSONObject bsonQuery){
        return new QuerySelector(bsonQuery);
    }

    public QuerySelector(){ super(); }

    public QuerySelector(BSONObject bsonQuery){
        super(bsonQuery);
    }

    public QuerySelector(String jsonQuery){
        super(jsonQuery);
    }

    public <T> QuerySelector(T modelQuery){
        super(modelQuery);
    }
}
