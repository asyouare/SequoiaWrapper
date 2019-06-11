package org.asyou.sequoia.dao;

import org.asyou.sequoia.*;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.query.QueryUtil;
import org.bson.BSONObject;

import java.util.List;

/**
 * 数据库适配器，用来获取数据库服务器及CURD操作器
 * Created by steven on 17/8/7.
 */
public class SequoiaAdapter {
    public static SequoiaAdapter create(String adapterId) throws SequoiaAdapterException {
        return new SequoiaAdapter(adapterId);
    }

//    private ThreadLocal<CollectionNameType> collectionNameType;
    private ThreadLocal<String> collectionName;
    private SequoiaHost sequoiaHost;

    /**
     * 适配器构造
     * @param adapterId 构造适配器时使用的adapterId，必须是在全局配置管理器中已存在的adapterId
     * @throws SequoiaAdapterException
     */
    public SequoiaAdapter(String adapterId) throws SequoiaAdapterException {
        if (!SequoiaHostManager.containsId(adapterId)) {
            throw new SequoiaAdapterException(String.format("SequoiaAdapter id'%s' is not found",adapterId));
        }
        this.sequoiaHost = SequoiaHostManager.getSequoiaHost(adapterId);
        this.collectionName = new ThreadLocal<>();
//        this.collectionNameType = new ThreadLocal<>();
    }

    public String getAdapterId() {
        return this.sequoiaHost.getAdapterId();
    }

    public SequoiaHost getHost(){
        return this.sequoiaHost;
    }

    //region CollectionName SpaceName绑定在Config中，每个Adapter对应一个SpaceName

    public String getSpaceName() {
        return sequoiaHost.getSpaceName();
    }

    public String getCollectionName() {
        return this.collectionName.get();
    }

    public SequoiaAdapter collection(String collectionName){
        this.collectionName.set(collectionName);
        return this;
    }

    public SequoiaAdapter collectionNameFromClass(Class<?> clazz){
        this.collectionName.set(QueryUtil.getCollectionName(clazz,this.collectionName.get()));
        return this;
    }

    public SequoiaAdapter clearCollectionName(){
        this.collectionName.remove();
        return this;
    }

    //space and collection list
    public List<String> getSpaceNames(){
        return getHost().getSpaceNames();
    }

    public List<String> getCollectionNames(){
        return getHost().getCollectionNames();
    }

    //endregion

    //region CollectionNameType 要同时指定SpaceName和CollectionName

//    public String getSpaceName() {
//        return collectionNameType.outsideOpt().getSpaceName();
//    }
//
//    public String getCollectionName() {
//        return collectionNameType.outsideOpt().getCollectionName();
//    }
//    public SequoiaAdapter collection(String collectionName){
//        this.collectionNameType.outsideOpt().setCollectionName(collectionName);
//        return this;
//    }
//
//    public SequoiaAdapter space(String spaceName){
//        this.collectionNameType.outsideOpt().setSpaceName(spaceName);
//        return this;
//    }
//
//    public SequoiaAdapter clearCollectionName(){
//        this.collectionNameType.remove();
//        return this;
//    }
//
//    /**
//     * 绑定CollectionSpace和Collection的名称
//     * @param clazz gson class
//     * @return SequoiaAdapter
//     */
//    public SequoiaAdapter collectionNameFromClass(Class<?> clazz){
//        this.collectionNameType.set(QueryUtil.getCollectionName(clazz,this.collectionNameType.outsideOpt()));
//        return this;
//    }

    //endregion

    /**
     * CRUD 操作
     */

    // Find

    public Find find(){
        return Find.create(this);
    }

    public Find find(String query){
        return Find.create(this, query);
    }

    public Find find(BSONObject query){
        return Find.create(this, query);
    }

    public <T> Find find(T query){
        return Find.create(this, query);
    }

    // Insert

    public Insert insert(){
        return Insert.create(this);
    }

    // Update

    public Update update(){ return Update.create(this); }

    //Delete

    public Delete delete() { return Delete.create(this); }

    //Count

    public Count count() {return Count.create(this);}

    //Aggregate

    public Aggregate aggregate(){
        return Aggregate.create(this);
    }


}
