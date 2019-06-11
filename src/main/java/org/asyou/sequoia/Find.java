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
import org.asyou.sequoia.model.Aggregates;
import org.asyou.sequoia.query.*;
import org.bson.BSONObject;
import org.bson.BasicBSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class Find {
    //region 构造器及内部成员
    public static Find create(SequoiaAdapter adapter){
        return new Find(adapter);
    }

    public static Find create(SequoiaAdapter adapter, String jsonQuery){
        return new Find(adapter, jsonQuery);
    }

    public static Find create(SequoiaAdapter adapter, BSONObject bsonQuery){
        return new Find(adapter, bsonQuery);
    }

    public static <T> Find create(SequoiaAdapter adapter, T modelQuery){
        return new Find(adapter, modelQuery);
    }

    private SequoiaAdapter adapter;

    private IQuery queryMatcher;
    private IQuery querySelector;
    private IQuery querySort;
    private IQuery queryIndex;

    private long skipCount = 0;
    private long limitCount = -1;
    private long totalCount = 0;
    private Class<?> clazz;

    private Find(SequoiaAdapter adapter){
        this.adapter = adapter;
    }

    private Find(SequoiaAdapter adapter, String jsonQuery){
        this.adapter = adapter;
        this.queryMatcher = QueryMatcher.create(jsonQuery);
    }

    private Find(SequoiaAdapter adapter, BSONObject bsonQuery){
        this.adapter = adapter;
        this.queryMatcher = QueryMatcher.create(bsonQuery);
    }

    private <T> Find(SequoiaAdapter adapter, T modelQuery){
        this.adapter = adapter;
        this.queryMatcher = QueryMatcher.create(modelQuery);
        this.clazz = modelQuery.getClass();
    }
    //endregion

    public Find matcher(IQuery matcher){
        this.queryMatcher = matcher;
        return this;
    }

    public Find selector(IQuery selector){
        this.querySelector = selector;
        return this;
    }

    public Find sort(IQuery sort){
        this.querySort = sort;
        return this;
    }

    public Find index(IQuery index){
        this.queryIndex = index;
        return this;
    }

    public Find desc(String... fieldNames){
        this.querySort = QuerySelector.create(Aggregates.sort(Arrays.asList(fieldNames), -1));
        return this;
    }

    public Find asc(String... fieldNames){
        this.querySort = QuerySelector.create(Aggregates.sort(Arrays.asList(fieldNames), 1));
        return this;
    }

    public Find skip(int skipCount){
        this.skipCount = skipCount;
        return this;
    }

    public Find limit(int limitCount){
        this.limitCount = limitCount;
        return this;
    }

    public Find as(Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public <T> T findOne() throws SequoiaAdapterException {
        this.skipCount = 0;
        this.limitCount = 1;
        List<T> list = getList(FindType.FindOne);
        if (list.size() < 1) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public <T> List<T> findMany() throws SequoiaAdapterException {
        return getList(FindType.FindMany);
    }

    public <T> Page<T> page(final long pageIndex, final long perPageCount) throws SequoiaAdapterException {
        this.skipCount = pageIndex * perPageCount;
        this.limitCount = perPageCount;
        List<T> list = getList(FindType.FindPage);
        Page<T> page = new Page(pageIndex, perPageCount, totalCount, list);
        return page;
    }

    private <T> List<T> getList(FindType findType) throws SequoiaAdapterException {
        List<T> list = new LinkedList<T>();
        Sequoiadb sdb = null;
        DBCollection collection = null;
        DBCursor cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        BSONObject matcherBson = queryMatcher == null ? new BasicBSONObject() : queryMatcher.toBSONObject();
        BSONObject selectorBson = querySelector == null ? null : querySelector.toBSONObject();
        BSONObject sortBson = querySort == null ? null : querySort.toBSONObject();
        BSONObject indexBson = queryIndex == null ? null : queryIndex.toBSONObject();

        try{
            sdb = adapter.getHost().getNewConnection();
            CollectionSpace space = adapter.getHost().getCollectionSpace(sdb, adapter.getSpaceName(), SequoiaAutoCreate.find.isAutoCreateSapce());
            collection = adapter.getHost().getCollection(space, adapter.getCollectionName(), SequoiaAutoCreate.find.isAutoCreateCollection());
            cursor = collection.query(matcherBson, selectorBson,
                    sortBson, indexBson,
                    skipCount, limitCount);
            if (clazz == null || clazz == String.class) {
                while (cursor.hasNext()) {
                    BSONObject record = cursor.getNext();
                    list.add((T) Convert.toJson(record));
                }
            } else {
                while (cursor.hasNext()) {
                    BSONObject record = cursor.getNext();
                    list.add((T) Convert.toModel(Convert.toJson(record), clazz));
                }
            }
            if (findType == FindType.FindPage) {
                totalCount = collection.getCount(matcherBson);
            }
        }catch (BaseException e) {
            throw SequoiaAdapterException.create(e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (sdb != null) {
                adapter.getHost().closeConnection(sdb);
            }
        }
        return list;
    }

    private enum FindType {
        FindOne(1), FindMany(2), FindPage(4);

        private int _value;
        private FindType(int value){
            this._value = value;;
        }
    }
}
