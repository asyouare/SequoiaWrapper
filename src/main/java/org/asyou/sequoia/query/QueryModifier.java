package org.asyou.sequoia.query;

import org.asyou.sequoia.model.Updates;
import org.bson.BSONObject;

/**
 * Created by steven on 17/10/17.
 */
public class QueryModifier extends AQueryBSONObject implements IQuery {

    public static QueryModifier create(){
        return new QueryModifier();
    }

    public static QueryModifier create(String jsonQuery){
        return new QueryModifier(jsonQuery);
    }

    public static <T> QueryModifier create(T modelQuery){
        return new QueryModifier(modelQuery);
    }

    public static QueryModifier create(BSONObject bsonQuery){
        return new QueryModifier(bsonQuery);
    }

    public QueryModifier(){}

    public QueryModifier(BSONObject bsonQuery){
        super(bsonQuery);
    }

    public QueryModifier(String jsonQuery){
        super(jsonQuery);
    }

    public <T> QueryModifier(T modelQuery){
        super(modelQuery);
    }

    @Override
    public BSONObject toBSONObject() {
        return Updates.set(bsonObject);
    }
}
