package org.asyou.sequoia;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;
import org.asyou.sequoia.common.Assertions;
import org.asyou.sequoia.common.Convert;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.dao.SequoiaAutoCreate;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.query.QueryUtil;
import org.bson.BSONObject;

import java.util.List;

/**
 * Created by steven on 17/8/7.
 */
public final class Insert {
    public static Insert create(SequoiaAdapter sequoiaAdapter){
        return new Insert(sequoiaAdapter);
    }

    private SequoiaAdapter adapter;
    private Class<?> clazz;

    //region 构造函数

    private Insert(SequoiaAdapter sequoiaAdapter){
        this.adapter = sequoiaAdapter;
    }

    //endregion

    public Insert as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    //返回1成功，0为失败，-1为异常
    private int insert(BSONObject value, List<BSONObject> values, boolean isInsertOne, boolean isTransaction) throws SequoiaAdapterException {
        Sequoiadb sdb = null;
        DBCollection collection = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));

        Assertions.notNull("Insert.insert() : collection name", adapter.getCollectionName());

        int result = 0;

        try{
            sdb = isTransaction ? adapter.getHost().getLocalConnection() : adapter.getHost().getNewConnection();
            CollectionSpace space = adapter.getHost().getCollectionSpace(sdb, adapter.getSpaceName(), SequoiaAutoCreate.insert.isAutoCreateSapce());
            collection = adapter.getHost().getCollection(space, adapter.getCollectionName(), SequoiaAutoCreate.insert.isAutoCreateCollection());
            if (isInsertOne) {
                collection.insert(value);
                result = 1;
            }else{
                collection.bulkInsert(values, 0);
                result = values.size();
            }
        }catch (BaseException e){
            result = -1;
            throw SequoiaAdapterException.create(e);
        } finally {
            if (!isTransaction) {
                if (sdb != null) {
                    adapter.getHost().closeConnection(sdb);
                }
            }
        }
        return result;
    }

    //no transaction

    public int insertOne(BSONObject bsonValue) throws SequoiaAdapterException {
        return insert(bsonValue, null, true, false);
    }

    public int insertOne(String jsonValue) throws SequoiaAdapterException {
        return insertOne(Convert.toModel(jsonValue));
    }

    public int insertOne(Object modelValue) throws SequoiaAdapterException {
        clazz = modelValue.getClass();
        return insertOne(Convert.toJson(modelValue));
    }

    public <T> int insertMany(List<T> listJsonValues) throws SequoiaAdapterException {
        return insert(null, Convert.transformList(listJsonValues), false, false);
    }

    //for transaction

    public int insertOneT(BSONObject bsonValue) throws SequoiaAdapterException {
        return insert(bsonValue, null, true, true);
    }

    public int insertOneT(String jsonValue) throws SequoiaAdapterException {
        return insertOneT(Convert.toModel(jsonValue));
    }

    public int insertOneT(Object modelValue) throws SequoiaAdapterException {
        clazz = modelValue.getClass();
        return insertOneT(Convert.toJson(modelValue));
    }

    public <T> int insertManyT(List<T> listJsonValues) throws SequoiaAdapterException {
        return insert(null, Convert.transformList(listJsonValues), false, true);
    }
}
