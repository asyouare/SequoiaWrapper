package org.asyou.sequoia.model;

import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.List;

import static org.asyou.sequoia.model.Commons.combine;
import static org.asyou.sequoia.model.Commons.outsideOpt;

/**
 * Created by steven on 17/10/10.
 */
public final class Aggregates {
    public static BSONObject project(String fieldName, int value){
        return outsideOpt(fieldName,"$project", value);
    }

    public static BSONObject project(BSONObject value){
        return Commons.createBSONObject("$project", value);
    }

    public static BSONObject match(BSONObject matcher){
        return Commons.createBSONObject("$match", matcher);
    }

    public static BSONObject limit(long limit){
        return Commons.createBSONObject("$limit", limit);
    }

    public static BSONObject sort(String fieldName, int value){
        return outsideOpt(fieldName,"$sort", value);
    }

    public static BSONObject sort(List<String> fieldNames, int value){
        BSONObject bsonObject = new BasicBSONObject();
        for(int i=0;i<fieldNames.size();i++)
            bsonObject.put(fieldNames.get(i),value);
        return sort(bsonObject);
    }

    public static BSONObject sort(BSONObject value){
        return Commons.createBSONObject("$sort", value);
    }

    public static BSONObject skip(long skip){
        return Commons.createBSONObject("$skip", skip);
    }

    public static BSONObject group(BSONObject groupExpression, BSONObject groupBson){
        return Commons.createBSONObject("$group", combine(new BasicBSONObject("_id",groupExpression),groupBson));
    }

    public static BSONObject group(BSONObject groupExpression, List<BSONField> bsonFieldList){
        BasicBSONObject basicBSONObject = new BasicBSONObject("_id", groupExpression);
        for(BSONField field : bsonFieldList){
            basicBSONObject.append(field.getName(), field.getValue());
        }
        return Commons.createBSONObject("$group", basicBSONObject);
    }
//
//    public static BSONObject group_addtoset(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).addtoset(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_avg(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).avg(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_sum(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).sum(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_count(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).count(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_max(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).max(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_min(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).min(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_first(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).first(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_last(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).last(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_push(String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).push(displayFieldName,targetFieldName);
//        return group(gp.toBsonObject());
//    }
//
//    public static BSONObject group_operate(String operateName, String groupFieldName, String displayFieldName, String targetFieldName){
//        Group gp = Group.create()._id(groupFieldName).append(operateName, displayFieldName, targetFieldName);
//        return group(gp.toBsonObject());
//    }

    /*
        group支持的聚集函数

        $addtoset	将字段添加到数组中，相同的字段值只会添加一次
        $first	取分组中第一条记录中的字段值
        $last	取分组中最后一条记录中的字段值
        $max	取分组中字段值最大的
        $min	取分组中字段值最小的
        $avg	取分组中字段值的平均值
        $push	将所有字段添加到数组中，即使数组中已经存在相同的字段值，也继续添加
        $sum	取分组中字段值的总和
        $count	对记录分组后，返回表所有的记录条数
    */
    public static class Group{
        public static Group create(){
            return new Group();
        }

        private BSONObject bsonObject;

        private Group(){
            this.bsonObject = new BasicBSONObject();
        }

        public Group _id(String groupFieldName){
            this.bsonObject.put("_id", groupFieldName);
            return this;
        }

        public Group _id(BSONObject groupFieldNames){
            this.bsonObject.put("_id", groupFieldNames);
            return this;
        }

        public Group addtoset(String displayFieldName, String targetFieldName){
            append("$addtoset", displayFieldName, targetFieldName);
            return this;
        }

        public Group first(String displayFieldName, String targetFieldName){
            append("$first", displayFieldName, targetFieldName);
            return this;
        }

        public Group last(String displayFieldName, String targetFieldName){
            append("$last", displayFieldName, targetFieldName);
            return this;
        }

        public Group max(String displayFieldName, String targetFieldName){
            append("$max", displayFieldName, targetFieldName);
            return this;
        }

        public Group min(String displayFieldName, String targetFieldName){
            append("$min", displayFieldName, targetFieldName);
            return this;
        }

        public Group avg(String displayFieldName, String targetFieldName){
            append("$avg", displayFieldName, targetFieldName);
            return this;
        }

        public Group push(String displayFieldName, String targetFieldName){
            append("$push", displayFieldName, targetFieldName);
            return this;
        }

        public Group sum(String displayFieldName, String targetFieldName){
            append("$sum", displayFieldName, targetFieldName);
            return this;
        }

        public Group count(String displayFieldName, String targetFieldName){
            append("$count", displayFieldName, targetFieldName);
            return this;
        }

        public Group append(String functionName, String displayFieldName, String targetFieldName){
            this.bsonObject.put(displayFieldName, new BasicBSONObject(functionName, "$" + targetFieldName));
            return this;
        }

        public BSONObject toBsonObject(){return this.bsonObject;}
    }
}
