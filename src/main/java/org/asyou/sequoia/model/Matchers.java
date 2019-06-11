package org.asyou.sequoia.model;

import org.asyou.sequoia.common.Assertions;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.asyou.sequoia.model.Commons.*;

/**
 * Created by steven on 17/9/25.
 */
public final class Matchers {

    public static <T> BSONObject gt(String fieldName, T value) {
        return insideOpt(fieldName,"$gt",value);
    }

    public static <T> BSONObject gte(String fieldName, T value) {
        return insideOpt(fieldName,"$gte",value);
    }

    public static <T> BSONObject lt(String fieldName, T value) {
        return insideOpt(fieldName,"$lt",value);
    }

    public static <T> BSONObject lte(String fieldName, T value) {
        return insideOpt(fieldName,"$lte",value);
    }

    public static <T> BSONObject ne(String fieldName, T value) {
        return insideOpt(fieldName,"$ne",value);
    }

    public static <T> BSONObject eq(String fieldName, T value) {
        return createBSONObject(fieldName, value);
    }

    public static <T> BSONObject et(String fieldName, T value) {
        return insideOpt(fieldName, "$et",value);
    }

    public static BSONObject mod(String fieldName, int mod, int value){
        return insideOpt(fieldName, "$mod", Arrays.asList(mod, value));
    }

    public static <T> BSONObject in(String fieldName, T... values) {
        return in(fieldName, Arrays.asList(values));
    }

    public static <T> BSONObject in(String fieldName, List<T> values) {
        return insideOpt(fieldName, "$in", values);
    }

    //isnNull：0返回字段存在且不为null的记录，1返回字段不存在或为null的记录
    public static BSONObject isnull(String fieldName, boolean isNull){
        int isNul = Assertions.zeroOrOne(isNull ? 1 : 0);
        return insideOpt(fieldName, "$isnull", isNul);
    }

    public static <T> BSONObject nin(String fieldName, T... values) {
        return nin(fieldName, Arrays.asList(values));
    }

    public static <T> BSONObject nin(String fieldName, List<T> values) {
        return insideOpt(fieldName, "$nin", values);
    }

    //$all 的操作对象是数组类型的字段名，选择fieldName包含所有给定数组“[ <值1>, <值2>, ... ]”中的值
    public static <T> BSONObject all(String fieldName, List<T> values){
        return insideOpt(fieldName, "$all", values);
    }

    public static BSONObject and(List<BSONObject> matchers) {
        return combine(matchers.toArray(new BSONObject[matchers.size()]));
    }

    public static BSONObject and(BSONObject... matchers) {
        return combine(matchers);
    }

    public static BSONObject $and(List<BSONObject> matchers) {
        return createBSONObject("$and", matchers);
    }

    public static BSONObject $and(BSONObject... matchers) {
        return $and(Arrays.asList(matchers));
    }

    public static BSONObject not(List<BSONObject> matchers){
        return createBSONObject("$not", matchers);
    }

    public static BSONObject not(BSONObject... matchers) {
        return not(Arrays.asList(matchers));
    }

    public static BSONObject notAny(List<BSONObject> matchers){
        return not(matchers);
    }

    public static BSONObject notAny(BSONObject... matchers){
        List<BSONObject> bson = new ArrayList<>();
        for(BSONObject bo : matchers){
            for(String key : bo.keySet()) {
                bson.add(createBSONObject(key, bo.get(key)));
            }
        }
        return notAny(bson);
    }

    public static BSONObject or(List<BSONObject> matchers) {
        return createBSONObject("$or", matchers);
    }

    public static BSONObject or(BSONObject... matchers) {
        return or(Arrays.asList(matchers));
    }

    public static BSONObject orAny(List<BSONObject> matchers){
        return or(matchers);
    }

    public static BSONObject orAny(BSONObject... matchers) {
        List<BSONObject> bson = new ArrayList<>();
        for(BSONObject bo : matchers){
            for(String key : bo.keySet()) {
                bson.add(createBSONObject(key, bo.get(key)));
            }
        }
        return orAny(bson);
    }

    //匹配集合中是否存在指定“<字段名>”的记录。
    //0	匹配“<字段名>”不存在的记录
    //1	匹配“<字段名>”存在的记录
    public static BSONObject exists(String fieldName, boolean isExist){
        int exist = Assertions.zeroOrOne(isExist ? 1 : 0);
        return insideOpt(fieldName, "$exists", exist);
    }

    public static BSONObject exists(String fieldName) {
        return exists(fieldName, true);
    }


    //选择集合中“<字段名>”匹配指定“{ <子字段>: <值> ... }”的记录
    public static BSONObject elemMatch(String fieldName, BSONObject matcher){
        return insideOpt(fieldName, "$elemMatch", matcher);
    }

    //用于数组对象，标识符是一个整数，如 $1，$3
    //示例： db.foo.bar.find( { "a.$1": 5 }, { "a": 1 } )
    private static BSONObject $plus(String fieldName, int $plus){
        //todo matchers.$plus
        return null;
    }

    //$regex 操作提供正则表达式模式匹配字符串查询功能。SequoiaDB 使用的是 PCRE 正则表达式。
    //$options
    //$options 提供四种选择标志：
    //i： 设置这个修饰符，模式中的字母进行大小写不敏感匹配。
    //m： 默认情况下，pcre认为目标字符串是由单行字符组成的，
    //    “行首”元字符（^）仅匹配字符串的开始位置，而“行末”元字符（$）仅匹配字符串末尾，或者最后的换行符。
    //    当这个修饰符设置之后，“行首”和“行末”就会匹配目标字符串中任意换行符之前或之后，
    //    另外，还分别匹配目标字符串的最开始和最末尾位置，如果目标字符串中没有“\n”，或者模式中没出现“^”或“$”，
    //    设置这个修饰符不产生任何影响。
    //x： 设置这个修饰符，模式中没有经过转义的或不在字符类中的空白数据字符总会被忽略，
    //    并且位于一个未转义的字符类外部的“#”字符和下一行换行符之间的字符也被忽略。
    //s： 设置这个修饰符，模式中的点号元字符匹配所有字符，包含换行符，如果没有这个修饰符，点号不匹配换行符。
    //示例：查询集合 foo.bar 下“str”字段值匹配不区分大小写的正则表达式 dh.*fj 的记录
    //db.foo.bar.find( { str: { $regex: 'dh.*fj', $options: 'i' } } )
    public static BSONObject regex(String fieldName, String pattern) {
        Assertions.notNull("pattern", pattern);
        return regex(fieldName, pattern, "i");
    }

    public static BSONObject regex(String fieldName, String pattern, String options) {
        Assertions.notNull("pattern", pattern);
//        Pattern pattern1 = Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
//        return new BasicBSONObject(fieldName, pattern1);
        BSONObject pat = new BasicBSONObject();
        pat.put("$regex", pattern);
        pat.put("$options", options);
        return new BasicBSONObject(fieldName, pat);
    }

    //$field 是字段符，选择满足“<字段名1>”匹配“<字段名2>”的记录。
    //查询 foo.bar 中“t1”字段值等于“t2”字段的记录：
    //示例：db.foo.bar.find( { "t1": { "$field": "t2" } } )
    //
    //可与其它匹配一起使用，达到这个效果。
    //查询 foo.bar 中“t1”字段值大于“t2”字段的记录：
    //示例：db.foo.bar.find( { "t1": { "$gt": { "$field": "t2" } } } )
    public static BSONObject field(String fieldName, String equalFieldName){
        return insideOpt(fieldName, "$field", equalFieldName);
//        return createBSONObject(fieldName, createBSONObject("$field", equalFieldName));
    }

    public static BSONObject field(String fieldName, String operator, String targetFieldName){
        return insideOpt(fieldName, operator, createBSONObject("$field", targetFieldName));
    }

    //$expand 将数组中的元素展开，生成多条记录返回。字段不是数组类型时，则忽略该操作。
    //{ <字段名>: { $expand: 1 } }中1没有特殊含义，仅作为占位符出现。
    public static BSONObject expand(String fieldName){
        return insideOpt(fieldName, "$expand", 1);
    }

    //{ <字段名>: { $returnMatch: <起始下标> } }
    //{ <字段名>: { $returnMatch: [ <起始下标>, <长度> ] } }
    //$returnMatch 选取匹配成功的数组元素，可以通过参数选取指定的元素。
    //必须结合对数组做展开匹配的匹配符使用($in, $all等，不支持$elemMatch)。
    //示例：db.foo.bar.find({a:{$returnMatch:0, $in:[1,4,7]}})
    //示例：db.foo.bar.find({a:{$returnMatch:[1,2], $in:[1,4,7]}})
    public static BSONObject returnMatch(String fieldName, int beginIndex, int length, BSONObject expression){
        BSONObject returnMatchBson = createBSONObject("$returnMatch", new int[]{beginIndex, length});
        returnMatchBson.putAll(expression);
        BSONObject bsonObject = createBSONObject(fieldName, returnMatchBson);
        return bsonObject;
    }

    public static BSONObject returnMatch(String fieldName, int beginIndex, BSONObject expression){
        BSONObject returnMatchBson = createBSONObject("$returnMatch", beginIndex);
        returnMatchBson.putAll(expression);
        BSONObject bsonObject = createBSONObject(fieldName, returnMatchBson);
        return bsonObject;
    }
}
