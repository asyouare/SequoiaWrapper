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
import org.asyou.sequoia.query.*;
import org.bson.BSONObject;

/**
 * Created by steven on 17/8/7.
 */
public class Update {
    public static Update create(SequoiaAdapter sequoiaAdapter){
        return new Update(sequoiaAdapter);
    }

    private SequoiaAdapter adapter;
    private IQuery queryMatcher;
    private IQuery queryModifier;
    private IQuery queryIndex;
    private Class<?> clazz;
    private BSONObject value;

    //region 构造函数

    private Update(SequoiaAdapter sequoiaAdapter){
        this.adapter = sequoiaAdapter;
    }

    //endregion

    public Update matcher(IQuery queryMatcher){
        this.queryMatcher = queryMatcher;
        return this;
    }

    public Update modifier(IQuery querySelector){
        this.queryModifier = querySelector;
        return this;
    }

    public Update index(IQuery queryIndex){
        this.queryIndex = queryIndex;
        return this;
    }

    public Update as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    private int update(boolean isUpdateOne, boolean isTransaction) throws SequoiaAdapterException{
        Sequoiadb sdb = null;
        DBCollection collection = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null", adapter.getCollectionName());

        BSONObject matcherBSONObject = queryMatcher == null ? null : queryMatcher.toBSONObject();
        BSONObject modifierBSONObject = queryModifier == null ? null : queryModifier.toBSONObject();
        BSONObject indexBSONObject = queryIndex == null ? null : queryIndex.toBSONObject();

        if ((null == modifierBSONObject || modifierBSONObject.isEmpty()) ||
                (null == matcherBSONObject || matcherBSONObject.isEmpty()))
            throw SequoiaAdapterException.create(SequoiaException.UPDATE_VALUE_NULL.toString());

        long count = adapter.count().matcher(queryMatcher).index(queryIndex).as(this.clazz).count();
        int result = 0;
        if (count > 0) {
            if (isUpdateOne){
                if (count != 1)
                    return 0;
            }else {
                if (count <= 0)
                    return 0;
            }
            try {
                sdb = isTransaction ? adapter.getHost().getLocalConnection() : adapter.getHost().getNewConnection();
                CollectionSpace space = adapter.getHost().getCollectionSpace(sdb, adapter.getSpaceName(), SequoiaAutoCreate.update.isAutoCreateSapce());
                collection = adapter.getHost().getCollection(space, adapter.getCollectionName(), SequoiaAutoCreate.update.isAutoCreateCollection());
                collection.update(matcherBSONObject, modifierBSONObject, indexBSONObject);
                if (isUpdateOne)
                    result = 1;
                else
                    result = (int) count;
            } catch (BaseException e) {
                result = -1;
                throw SequoiaAdapterException.create(e);
            } finally {
                if (!isTransaction) {
                    if (sdb != null)
                        adapter.getHost().closeConnection(sdb);
                }
            }
        }
        return result;
    }

    //no transaction

    public int update(boolean isUpdateOne) throws SequoiaAdapterException {
        return update(isUpdateOne, false);
    }

    public int updateOne() throws SequoiaAdapterException {
        return update(true);
    }

    public int updateOne(String jsonQuery, String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        this.queryModifier = QueryModifier.create(jsonValue);
        return update(true);
    }

    public int updateOne(BSONObject bsonQuery, BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        this.queryModifier = QueryModifier.create(bsonValue);
        return update(true);
    }

    public <T> int updateOne(T modelQuery, T modelValue) throws SequoiaAdapterException {
        clazz = modelQuery.getClass();
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.queryModifier = QueryModifier.create(modelValue);
        return update(true);
    }

    public int updateMany() throws SequoiaAdapterException {
        return update(false);
    }

    public int updateMany(String jsonQuery, String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        this.queryModifier = QueryModifier.create(jsonValue);
        return update(false);
    }

    public int updateMany(BSONObject bsonQuery, BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        this.queryModifier = QueryModifier.create(bsonValue);
        return update(false);
    }

    public <T> int updateMany(T modelQuery, T modelValue) throws SequoiaAdapterException {
        clazz = modelQuery.getClass();
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.queryModifier = QueryModifier.create(modelValue);
        return update(false);
    }

    //for transaction

    public int updateT(boolean isUpdateOne) throws SequoiaAdapterException {
        return update(isUpdateOne, true);
    }

    public int updateOneT() throws SequoiaAdapterException {
        return updateT(true);
    }

    public int updateOneT(String jsonQuery, String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        this.queryModifier = QueryModifier.create(jsonValue);
        return updateT(true);
    }

    public int updateOneT(BSONObject bsonQuery, BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        this.queryModifier = QueryModifier.create(bsonValue);
        return updateT(true);
    }

    public <T> int updateOneT(T modelQuery, T modelValue) throws SequoiaAdapterException {
        clazz = modelQuery.getClass();
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.queryModifier = QueryModifier.create(modelValue);
        return updateT(true);
    }

    public int updateManyT() throws SequoiaAdapterException {
        return updateT(false);
    }

    public int updateManyT(String jsonQuery, String jsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        this.queryModifier = QueryModifier.create(jsonValue);
        return updateT(false);
    }

    public int updateManyT(BSONObject bsonQuery, BSONObject bsonValue) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        this.queryModifier = QueryModifier.create(bsonValue);
        return updateT(false);
    }

    public <T> int updateManyT(T modelQuery, T modelValue) throws SequoiaAdapterException {
        clazz = modelQuery.getClass();
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.queryModifier = QueryModifier.create(modelValue);
        return updateT(false);
    }
}
