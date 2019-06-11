package org.asyou.sequoia.query;

import org.asyou.sequoia.common.Convert;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

/**
 * Created by steven on 17/10/10.
 */
public abstract class AQueryBSONObject {

    static {
        Convert.check_and_init();
    }

    protected BSONObject bsonObject;

    public BSONObject getBsonObject() {
        return bsonObject;
    }

    public void setBsonObject(BSONObject bsonObject) {
        this.bsonObject = bsonObject;
    }

    protected AQueryBSONObject(){ this.bsonObject = new BasicBSONObject(); }

    protected AQueryBSONObject(BSONObject bsonObject){
//        if (bsonObject == null || bsonObject.isEmpty())
//            this.bsonObject = new BasicBSONObject();
//        else
//            this.bsonObject = Convert.toModel(Convert.toJson(bsonObject));
        this.bsonObject = bsonObject;
    }

    protected AQueryBSONObject(String jsonObject){
        BSONObject bsonObject1 = Convert.toModel(jsonObject);
        this.bsonObject = bsonObject1;
    }

    protected <T> AQueryBSONObject(T modelObject){
        BSONObject bsonObject1 = Convert.toModel(Convert.toJson(modelObject));
        this.bsonObject = bsonObject1;
    }


    public BSONObject toBSONObject() {
        return this.bsonObject;
    }

    @Override
    public String toString(){ return Convert.toJson(this.bsonObject); }

}
