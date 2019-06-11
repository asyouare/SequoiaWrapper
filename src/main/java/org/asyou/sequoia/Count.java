package org.asyou.sequoia;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.DBCursor;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.exception.BaseException;
import org.asyou.sequoia.common.Assertions;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.dao.SequoiaAutoCreate;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.query.IQuery;
import org.asyou.sequoia.query.QueryMatcher;
import org.asyou.sequoia.query.QueryUtil;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

/**
 * Created by steven on 17/8/7.
 */
public class Count {
    public static Count create(SequoiaAdapter sequoiaAdapter){
        return new Count(sequoiaAdapter);
    }

    private SequoiaAdapter adapter;
    private IQuery queryMatcher;
    private IQuery queryIndex;
    private Class<?> clazz;

    //region 构造函数

    private Count(SequoiaAdapter sequoiaAdapter){
        this.adapter = sequoiaAdapter;
    }

    //endregion

    public Count matcher(IQuery queryMatcher){
        this.queryMatcher = queryMatcher;
        return this;
    }

    public Count index(IQuery queryIndex){
        this.queryIndex = queryIndex;
        return this;
    }

    public Count as(final Class<?> clz) {
        this.clazz = clz;
        return this;
    }

    public long count(String jsonQuery) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(jsonQuery);
        return this.count();
    }

    public long count(BSONObject bsonQuery) throws SequoiaAdapterException {
        this.queryMatcher = QueryMatcher.create(bsonQuery);
        return this.count();
    }

    public <T> long count(T modelQuery) throws SequoiaAdapterException {
        this.clazz = modelQuery.getClass();
        this.queryMatcher = QueryMatcher.create(modelQuery);
        return this.count();
    }

    public long count() throws SequoiaAdapterException {
        Sequoiadb sdb = null;
        DBCollection collection = null;
        DBCursor cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        BSONObject matcherBSONObject = queryMatcher == null ? new BasicBSONObject() : queryMatcher.toBSONObject();
        BSONObject indexBSONObject = queryIndex == null ? null : queryIndex.toBSONObject();

        long count = 0;
        try{
            sdb = adapter.getHost().getNewConnection();
            CollectionSpace space = adapter.getHost().getCollectionSpace(sdb, adapter.getSpaceName(), SequoiaAutoCreate.find.isAutoCreateSapce());
            collection = adapter.getHost().getCollection(space, adapter.getCollectionName(), SequoiaAutoCreate.find.isAutoCreateCollection());
            count = collection.getCount(matcherBSONObject, indexBSONObject);
        }catch (BaseException e){
            throw SequoiaAdapterException.create(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sdb != null) {
                adapter.getHost().closeConnection(sdb);
            }
        }
        return count;
    }

}
