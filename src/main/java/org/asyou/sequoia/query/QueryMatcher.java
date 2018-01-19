package org.asyou.sequoia.query;

import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.model.Matchers;
import org.asyou.sequoia.type.DateFromTo;
import org.asyou.sequoia.type.DateTimeFromTo;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.Set;

import static org.asyou.sequoia.model.Matchers.*;

/**
 * Created by steven on 17/9/20.
 */
public final class QueryMatcher extends AQueryBSONObject implements IQuery{

    public static QueryMatcher create(){
        return new QueryMatcher();
    }

    public static QueryMatcher create(String jsonQuery){
        return new QueryMatcher(jsonQuery);
    }

    public static <T> QueryMatcher create(T modelQuery){
        return new QueryMatcher(modelQuery);
    }

    public static QueryMatcher create(BSONObject bsonQuery){
        return new QueryMatcher(bsonQuery);
    }


    public QueryMatcher(){ super(); }

    public QueryMatcher(BSONObject bsonQuery){
        super(bsonQuery);
    }

    public QueryMatcher(String jsonQuery){
        super(jsonQuery);
    }

    public <T> QueryMatcher(T modelQuery){
        super(modelQuery);
    }

    public QueryMatcher dateFromTo(DateFromTo fromTo){
        BasicBSONObject bson = (BasicBSONObject) this.bsonObject;

        BSONObject bsonFromTo = new BasicBSONObject();
        bsonFromTo.put("$gte", fromTo.getFrom());
        bsonFromTo.put("$lte", fromTo.getTo());

        BSONObject dateFromToBson = eq(fromTo.getFieldName(), bsonFromTo);

        for(String dateKey : dateFromToBson.keySet()){
            bson.append(dateKey, dateFromToBson.get(dateKey));
        }
        return this;
    }

    public QueryMatcher dateTimeFromTo(DateTimeFromTo fromTo){
        BasicBSONObject bson = (BasicBSONObject) this.bsonObject;

        BSONObject bsonFromTo = new BasicBSONObject();
        bsonFromTo.put("$gte", fromTo.getFrom());
        bsonFromTo.put("$lte", fromTo.getTo());

        BSONObject dateTimeFromToBson = eq(fromTo.getFieldName(), bsonFromTo);

        for(String dateKey : dateTimeFromToBson.keySet()){
            bson.append(dateKey, dateTimeFromToBson.get(dateKey));
        }
        return this;
    }

    public QueryMatcher and(){
        this.bsonObject = Matchers.and(bsonObject);
        return this;
    }

    public QueryMatcher or(){
        this.bsonObject = Matchers.orAny(bsonObject);
        return this;
    }

    public QueryMatcher not(){
        this.bsonObject = Matchers.notAny(bsonObject);
        return this;
    }

    public QueryMatcher contain(){
        BasicBSONObject bson = (BasicBSONObject) this.bsonObject;
        Set<String> keySet = bsonObject.keySet();
        for(String key : keySet){
            if (bsonObject.get(key) instanceof String){
                BSONObject rbson = regex(key,bsonObject.get(key).toString());
                for(String rkey : rbson.keySet()){
                    bson.put(rkey, rbson.get(rkey));
                }
            }
        }
        return this;
    }


}
