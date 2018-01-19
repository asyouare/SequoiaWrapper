package org.asyou.sequoia.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.asyou.sequoia.serializer.bson.*;
import org.asyou.sequoia.serializer.gson.*;
import org.asyou.sequoia.type.DecimalObject;
import org.bson.BSON;
import org.bson.BSONObject;
import org.bson.types.BSONTimestamp;
import org.bson.types.ObjectId;
import org.bson.util.JSON;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by steven on 17/7/17.
 */
public final class Convert {
    private static Gson gson = null;
    private static JsonParser jsonParser = null;

    static {
        init_gson_builder();
    }

    private static void init_gson_builder(){
        //Gson init
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class,new ObjectIdTypeAdapter());
        builder.registerTypeAdapter(Date.class,new DateTypeAdapter());
        builder.registerTypeAdapter(LocalDate.class,new LocalDateTypeAdapter());
        builder.registerTypeAdapter(LocalDateTime.class,new LocalDateTimeTypeAdapter());
        builder.registerTypeAdapter(BSONTimestamp.class,new BSONTimestampTypeAdapter());
        builder.registerTypeAdapter(BigDecimal.class,new BigDecimalTypeAdapter());
        builder.registerTypeAdapter(DecimalObject.class,new DecimalObjectTypeAdapter());
        builder.setDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSS");
        gson = builder.create();

        //JsonParser init
        jsonParser = new JsonParser();

        //BSON EncodingHook init
        BSON.addEncodingHook(LocalDate.class, new LocalDateTransformer());
        BSON.addDecodingHook(LocalDate.class, new LocalDateTransformer());
        BSON.addEncodingHook(LocalDateTime.class, new LocalDateTimeTransformer());
        BSON.addDecodingHook(LocalDateTime.class, new LocalDateTimeTransformer());
    }

    public static void check_and_init(){
        if (null == gson || null == jsonParser)
            init_gson_builder();
    }

    public static void setGsonBuilder(GsonBuilder gsonBuilder){
        gson = gsonBuilder.create();
    }

    public static void setGson(Gson gson1){
        gson = gson1;
    }

    public static Gson getGson(){
        return gson;
    }

    public static <T> T toModel(String json, Class<T> clazz){
        return gson.fromJson(json, clazz == null ? String.class : clazz);
    }

    public static <T> T toModel(BSONObject bsonObject, Class<T> clazz){
        return gson.fromJson(gson.toJson(bsonObject), clazz);
    }

    public static BSONObject toModel(String json){
//        Map<String, Object> map = gson.toModel(json, new TypeToken<Map<String, Object>>() {}.getType());
        BSONObject bo = (BSONObject) JSON.parse(json);
        return bo;
    }

//    public static List<BSONObject> toModel(List<String> jsonList){
//        List<BSONObject> bsonList = new ArrayList<>();
//        for(String json : jsonList){
//            bsonList.add(Convert.toModel(json));
//        }
//        return bsonList;
//    }

    public static BSONObject toModel(Object model){
        return Convert.toModel(Convert.toJson(model));
    }

    // T:String to BSONObject
    // T:BSONObject to String
    // T:Object to String to BSONObject
    public static <R, T> List<R> transformList(List<T> list){
        List<R> resultList = new ArrayList<R>();
        for(T t : list){
            if (t instanceof String) {
                //如果输入String，则输出R
                resultList.add((R) Convert.toModel((String)t));
            }else if (t instanceof BSONObject){
                //如果输入BSONObject，则输出String
                resultList.add((R) Convert.toJson(t));
            }else{
                String jsonStr = Convert.toJson(t);
                resultList.add((R) Convert.toModel(jsonStr));
            }
        }
        return resultList;
    }

    public static <R, T> R transform(T input){
        if (input instanceof String){
            //如果输入String, 则输出BSONObject
            return (R) toModel((String) input);
        }else{
            //如果输入BSONObject, 则输出String
            return (R) toJson(input);
        }
    }


    /**
     * 把model转换成json格式，如果源model本身是string类型，直接返回，如果格式不正确，返回null
     * @param model 源model
     * @return json
     */
    public static String toJson(Object model){
        if (model instanceof String) {
            return model.toString();
        }
        return gson.toJson(model);
    }

    public static <T> String toJson(List<T> list){
        if (list.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            for (T t : list)
                stringBuilder.append(toJson(t) + ",");
            return stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("]").toString();
        }else{
            return "[]";
        }
    }

    public static <T> List<String> toJsonList(List<T> list){
        List<String> jsonList = new ArrayList<>();
        for (T t : list)
            jsonList.add(Convert.toJson(t));
        return jsonList;
    }

    public static boolean isJson(String json) {
        if (StringUtils.isBlank(json)) {
            return false;
        }
        try {
            jsonParser.parse(json);
            return true;
        } catch (JsonParseException e) {
            //todo insert a log
            return false;
        }
    }
}
