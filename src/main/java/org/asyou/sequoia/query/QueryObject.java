package org.asyou.sequoia.query;

import org.asyou.sequoia.type.DateFromTo;
import org.asyou.sequoia.type.DateTimeFromTo;
import org.bson.BSONObject;

public class QueryObject extends AQueryBSONObject implements IQuery {
    public static QueryObject create(){
        return new QueryObject();
    }

    public static QueryObject create(String jsonQuery){
        return new QueryObject(jsonQuery);
    }

    public static <T> QueryObject create(T modelQuery){
        return new QueryObject(modelQuery);
    }

    public static QueryObject create(BSONObject bsonQuery){
        return new QueryObject(bsonQuery);
    }

    public QueryObject(){ super(); }

    public QueryObject(BSONObject bsonQuery){
        super(bsonQuery);
    }

    public QueryObject(String jsonQuery){
        super(jsonQuery);
    }

    public <T> QueryObject(T modelQuery){
        super(modelQuery);
    }
    
}
