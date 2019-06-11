package org.asyou.sequoia.query;

import org.asyou.sequoia.common.Assertions;
import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.model.Accumulators;
import org.asyou.sequoia.model.Aggregates;
import org.asyou.sequoia.model.BSONField;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 17/10/17.
 */
public class QueryAggregate{

    public static QueryAggregate create(){
        return new QueryAggregate();
    }

    public static QueryAggregate create(BSONObject match){
        return new QueryAggregate(match);
    }

    public static QueryAggregate create(String match){
        return new QueryAggregate(match);
    }

    public static <T> QueryAggregate create(T match){
        return new QueryAggregate(match);
    }

    private BSONObject match;
    private Group group;

    public QueryAggregate(){ }

    public QueryAggregate(BSONObject match){
        this.match(match);
    }

    public QueryAggregate(String match){
        this.match(match);
    }

    public <T> QueryAggregate(T match){
        this.match(match);
    }

    public List<BSONObject> toBsonList(){
        Assertions.notNull("QueryAggregate group expression", this.group);
        if (null == this.match) {
            this.match();
        }
        List<BSONObject> list = new ArrayList<>();
        list.add(this.match);
        list.add(this.group.toBSONObject());
        return list;
    }

    public QueryAggregate match(){
        match = Aggregates.match(new BasicBSONObject());
        return this;
    }

    public QueryAggregate match(BSONObject bsonMatch){
        match = Aggregates.match(bsonMatch);
        return this;
    }

    public QueryAggregate match(String jsonMatch){
        match = Aggregates.match(Convert.toModel(jsonMatch));
        return this;
    }

    public <T> QueryAggregate match(T modelMatch){
        match = Aggregates.match(Convert.toModel(modelMatch));
        return this;
    }

    public Group group(){
        return group(null);
    }

    public Group group(BSONObject expression){
        if (null == this.group) {
            this.group = new Group(this, expression);
        }
        return this.group;
    }

    public Group newGroup(){
        return newGroup(null);
    }

    public Group newGroup(BSONObject expression){
        this.group = new Group(this, expression);
        return this.group;
    }

    public static class Group extends AQueryBSONObject implements IQuery{
        private QueryAggregate parentObject;
        private List<BSONField> listBsonField;
        private BSONObject groupExpression;
        private char dollar;

        private Group(QueryAggregate parentObject){ this(parentObject, null); }

        private Group(QueryAggregate parentObject, BSONObject groupExpression){
            this.parentObject = parentObject;
            this.listBsonField = new ArrayList<>();
            this.groupExpression = groupExpression;
            this.bsonObject = new BasicBSONObject();
            this.dollar = QuerySymbol.dollar;
        }

        public Group sum(String targetFieldName){
            return sum(targetFieldName, "sum");
        }

        public Group sum(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.sum(displayName, dollar + targetFieldName));
            return this;
        }

        public Group avg(String targetFieldName){
            return avg(targetFieldName, "avg");
        }

        public Group avg(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.avg(displayName, dollar + targetFieldName));
            return this;
        }

        public Group first(String targetFieldName){
            return first(targetFieldName, "first");
        }

        public Group first(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.first(displayName, dollar + targetFieldName));
            return this;
        }

        public Group last(String targetFieldName){
            return last(targetFieldName, "last");
        }

        public Group last(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.last(displayName, dollar + targetFieldName));
            return this;
        }

        public Group max(String targetFieldName){
            return max(targetFieldName, "max");
        }

        public Group max(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.max(displayName, dollar + targetFieldName));
            return this;
        }

        public Group min(String targetFieldName){
            return min(targetFieldName, "min");
        }

        public Group min(String targetFieldName, String displayName){
            listBsonField.add(Accumulators.min(displayName, dollar + targetFieldName));
            return this;
        }

        public Group count(String targetFieldName){
            return count(targetFieldName, "count");
        }

        public Group count(String targetFieldName,String displayName){
            listBsonField.add(Accumulators.count(displayName, dollar + targetFieldName));
            return this;
        }

        public QueryAggregate parent(){
            return this.parentObject;
        }

        @Override
        public BSONObject toBSONObject(){
            this.bsonObject = Aggregates.group(groupExpression, listBsonField);
            return this.bsonObject;
        }

    }
}
