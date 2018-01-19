package org.asyou.sequoia;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;
import org.asyou.sequoia.common.Assertions;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.dao.SequoiaAutoCreate;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.exception.SequoiaException;
import org.asyou.sequoia.query.IQuery;
import org.asyou.sequoia.query.QueryMatcher;
import org.asyou.sequoia.query.QueryUtil;
import org.bson.BSONObject;

/**
 * Created by steven on 17/8/7.
 */
public final class Delete {
    public static Delete create(SequoiaAdapter sequoiaAdapter){
        return new Delete(sequoiaAdapter);
    }

    public static <T> Delete create(SequoiaAdapter sequoiaAdapter, T modelMatcher){
        return new Delete(sequoiaAdapter, modelMatcher);
    }

    public static <T> Delete create(SequoiaAdapter sequoiaAdapter, String jsonMatcher){
        return new Delete(sequoiaAdapter, jsonMatcher);
    }

    public static <T> Delete create(SequoiaAdapter sequoiaAdapter, BSONObject bsonMatcher){
        return new Delete(sequoiaAdapter, bsonMatcher);
    }


    private SequoiaAdapter adapter;
    private IQuery queryMatcher;
    private IQuery queryIndex;
    private Class<?> clazz;

    //region 构造函数

    private Delete(SequoiaAdapter sequoiaAdapter){
        this.adapter = sequoiaAdapter;
    }

    private <T> Delete(SequoiaAdapter sequoiaAdapter, T modelQuery){
        this.adapter = sequoiaAdapter;
        this.queryMatcher = QueryMatcher.create(modelQuery);
        clazz = modelQuery.getClass();
    }

    private Delete(SequoiaAdapter sequoiaAdapter, String jsonQuery){
        this.adapter = sequoiaAdapter;
        this.queryMatcher = QueryMatcher.create(jsonQuery);
    }

    private Delete(SequoiaAdapter sequoiaAdapter, BSONObject bsonQuery){
        this.adapter = sequoiaAdapter;
        this.queryMatcher = QueryMatcher.create(bsonQuery);
    }

    //endregion

    public Delete matcher(IQuery queryMatcher){
        this.queryMatcher = queryMatcher;
        return this;
    }

    public Delete index(IQuery queryIndex){
        this.queryIndex = queryIndex;
        return this;
    }

    //返回1成功，0为失败，-1为异常
    public Delete as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    private int delete(boolean isDeleteOne, boolean isTransaction) throws SequoiaAdapterException {
        Sequoiadb sdb = null;
        DBCollection collection = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null", adapter.getCollectionName());

        BSONObject matcherBSONObject = queryMatcher == null ? null : queryMatcher.toBSONObject();
        BSONObject indexBSONObject = queryIndex == null ? null : queryIndex.toBSONObject();

        if (null == matcherBSONObject || matcherBSONObject.isEmpty())
            throw SequoiaAdapterException.create(SequoiaException.DELETE_VALUE_NULL.toString());

        long count = adapter.count().matcher(queryMatcher).index(queryIndex).as(this.clazz).count();
        int result = 0;
        if (count > 0) {
            if (isDeleteOne){
                if (count != 1)
                    return 0;
            }else {
                if (count <= 0)
                    return 0;
            }
            try {
                sdb = isTransaction ? adapter.getHost().getLocalConnection() : adapter.getHost().getNewConnection();
                CollectionSpace space = adapter.getHost().getCollectionSpace(sdb, adapter.getSpaceName(), SequoiaAutoCreate.delete.isAutoCreateSapce());
                collection = adapter.getHost().getCollection(space, adapter.getCollectionName(), SequoiaAutoCreate.delete.isAutoCreateCollection());
                collection.delete(matcherBSONObject, indexBSONObject);
                if (isDeleteOne)
                    result = 1;
                else
                    result = (int) count;
            } catch (BaseException e) {
                result = -1;
                throw SequoiaAdapterException.create(e);
            } finally {
                if (!isTransaction) {
                    if (sdb != null) {
                        adapter.getHost().closeConnection(sdb);
                    }
                }
            }
        }
        return result;
    }

    //no transaction

    public int deleteOne() throws SequoiaAdapterException {
        return delete(true, false);
    }

    public int deleteMany() throws SequoiaAdapterException {
        return delete(false, false);
    }

    public int deleteOne(String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonValue);
        return delete(true, false);
    }

    public int deleteMany(String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonValue);
        return delete(false, false);
    }

    public int deleteOne(BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonValue);
        return delete(true, false);
    }

    public int deleteMany(BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonValue);
        return delete(false, false);
    }

    public <T> int deleteOne(T modelValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(modelValue);
        return delete(true, false);
    }

    public <T> int deleteMany(T modelValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(modelValue);
        return delete(false, false);
    }

    //for transaction

    public int deleteOneT() throws SequoiaAdapterException {
        return delete(true, true);
    }

    public int deleteManyT() throws SequoiaAdapterException {
        return delete(false, true);
    }

    public int deleteOneT(String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonValue);
        return delete(true, true);
    }

    public int deleteManyT(String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonValue);
        return delete(false, true);
    }

    public int deleteOneT(BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonValue);
        return delete(true, true);
    }

    public int deleteManyT(BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonValue);
        return delete(false, true);
    }

    public <T> int deleteOneT(T modelValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(modelValue);
        return delete(true, true);
    }

    public <T> int deleteManyT(T modelValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(modelValue);
        return delete(false, true);
    }
}
