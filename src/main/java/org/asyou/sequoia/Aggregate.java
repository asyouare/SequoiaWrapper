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
import org.asyou.sequoia.query.QueryAggregate;
import org.asyou.sequoia.query.QueryUtil;
import org.bson.BSONObject;

import java.util.*;

/**
 * Created by steven on 17/10/16.
 */
public class Aggregate {
    public static Aggregate create(SequoiaAdapter sequoiaAdapter){
        return new Aggregate(sequoiaAdapter);
    }

    private SequoiaAdapter adapter;
    private QueryAggregate queryAggregate;
    private Class<?> clazz;

    //region 构造函数

    private Aggregate(SequoiaAdapter sequoiaAdapter){
        this.adapter = sequoiaAdapter;
    }

    //endregion

    public QueryAggregate getQueryAggregate() {
        if (null == this.queryAggregate) {
            this.queryAggregate = QueryAggregate.create();
        }
        return queryAggregate;
    }

    public Aggregate matcher(QueryAggregate queryAggregate){
        this.queryAggregate = queryAggregate;
        return this;
    }

    public Aggregate as(final Class<?> clz){
        this.clazz = clz;
        return this;
    }

    public List<BSONObject> aggregate(List<BSONObject> queryList) throws SequoiaAdapterException {
        Sequoiadb sdb = null;
        DBCollection collection = null;
        DBCursor cursor = null;
        adapter.collection(QueryUtil.getCollectionName(clazz, adapter.getCollectionName()));
        Assertions.notNull("target class and collection name is null, use as(Class)", adapter.getCollectionName());

        List<BSONObject> list = new ArrayList<>();
        try{
            sdb = adapter.getHost().getNewConnection();
            CollectionSpace space = adapter.getHost().getCollectionSpace(sdb, adapter.getSpaceName(), SequoiaAutoCreate.find.isAutoCreateSapce());
            collection = adapter.getHost().getCollection(space, adapter.getCollectionName(), SequoiaAutoCreate.find.isAutoCreateCollection());
            cursor = collection.aggregate(queryList);
            while (cursor.hasNext()) {
                list.add(cursor.getNext());
            }
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
        return list;
    }

    public Map<String, Number> values(List<BSONObject> queryList) throws SequoiaAdapterException {
        Map<String,Number> map = new LinkedHashMap<>();
        List<BSONObject> resultList = aggregate(queryList);
        if (null != resultList){
            int size = resultList.size();
            for(int i=0;i<size;i++){
                if (resultList.get(i).containsField("_id")) {
                    resultList.get(i).removeField("_id");
                }
                Set<String> keys = resultList.get(i).keySet();
                for(String key : keys){
                    map.put(key, (Number) resultList.get(i).get(key));
                }
            }
        }
        return map;
    }

    public Map<String, Number> values() throws SequoiaAdapterException {
        return values(this.queryAggregate.toBsonList());
    }

    public Number sum(String fieldName) throws SequoiaAdapterException {
        String displayName = "sum";
        getQueryAggregate().newGroup().sum(fieldName, displayName);
        return values().get(displayName);
    }

    public Number avg(String fieldName) throws SequoiaAdapterException {
        String displayName = "avg";
        getQueryAggregate().newGroup().avg(fieldName, displayName);
        return values().get(displayName);
    }

    public Number first(String fieldName) throws SequoiaAdapterException {
        String displayName = "first";
        getQueryAggregate().newGroup().first(fieldName, displayName);
        return values().get(displayName);
    }

    public Number last(String fieldName) throws SequoiaAdapterException {
        String displayName = "last";
        getQueryAggregate().newGroup().last(fieldName, displayName);
        return values().get(displayName);
    }

    public Number max(String fieldName) throws SequoiaAdapterException {
        String displayName = "max";
        getQueryAggregate().newGroup().max(fieldName, displayName);
        return values().get(displayName);
    }

    public Number min(String fieldName) throws SequoiaAdapterException {
        String displayName = "min";
        getQueryAggregate().newGroup().min(fieldName, displayName);
        return values().get(displayName);
    }
}
