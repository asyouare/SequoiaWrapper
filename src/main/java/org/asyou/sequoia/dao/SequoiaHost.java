package org.asyou.sequoia.dao;

import com.sequoiadb.base.CollectionSpace;
import com.sequoiadb.base.DBCollection;
import com.sequoiadb.base.Sequoiadb;
import com.sequoiadb.base.SequoiadbDatasource;
import com.sequoiadb.exception.BaseException;
import org.asyou.sequoia.base.Config;
import org.asyou.sequoia.exception.SequoiaAdapterException;
import org.asyou.sequoia.exception.SequoiaAssertions;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库服务器类，配置数据库，
 * Created by steven on 17/8/7.
 */
public class SequoiaHost {
    private Config config;
    private SequoiadbDatasource datasource;
    private ThreadLocal<Sequoiadb> connection;

    public SequoiaHost(Config config) throws SequoiaAdapterException {
        try {
            this.config = config.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new SequoiaAdapterException(e.getMessage());
        }
        connection = new ThreadLocal<>();
        datasource = new SequoiadbDatasource(this.config.getAddressList(),
                this.config.getUserName(),this.config.getPassword(),
                this.config.getConfigOptions(),
                this.config.getDatasourceOptions());
    }

    public Config getConfig(){
        return this.config;
    }

    public String getAdapterId(){ return config.getAdapterId(); }

    public String getSpaceName(){ return config.getSpaceName(); }

    public String toString(){ return this.config.toString(); }

    public Sequoiadb getLocalConnection() throws SequoiaAdapterException {
        if (null == connection.get()) {
            try {
                connection.set(datasource.getConnection());
            } catch (InterruptedException e) {
                throw SequoiaAdapterException.create(e);
            }
        }
        return connection.get();
    }

    public Sequoiadb getNewConnection() throws SequoiaAdapterException {
        Sequoiadb sdb = null;
        try{
            sdb = datasource.getConnection();
        } catch (BaseException e) {
            throw SequoiaAdapterException.create(e);
        } catch (InterruptedException e) {
            throw SequoiaAdapterException.create(e);
        }
        return SequoiaAssertions.notNull("Sequoiadb", sdb);
    }

    public CollectionSpace createCollectionSpace(String spaceName) throws SequoiaAdapterException {
        Sequoiadb sdb = getNewConnection();
        CollectionSpace space = null;
        try{
            space = sdb.createCollectionSpace(spaceName);
        }catch (BaseException e){
            throw SequoiaAdapterException.create(e);
        }
        return SequoiaAssertions.notNull("CollectionSpace", space);
    }

    public DBCollection createCollection(String spaceName, String collectionName, boolean autoCreate) throws SequoiaAdapterException {
        Sequoiadb sdb = getNewConnection();
        CollectionSpace space = getCollectionSpace(sdb, spaceName, autoCreate);
        DBCollection collection = null;
        try {
            collection = space.createCollection(collectionName);
        }catch (BaseException e){
            throw SequoiaAdapterException.create(e);
        }
        return SequoiaAssertions.notNull("Collection", collection);
    }

    public DBCollection createCollection(CollectionSpace space, String collectionName) throws SequoiaAdapterException {
        Sequoiadb sdb = getNewConnection();
        DBCollection collection = null;
        try {
            collection = space.createCollection(collectionName);
        }catch (BaseException e){
            throw SequoiaAdapterException.create(e);
        }
        return SequoiaAssertions.notNull("Collection", collection);
    }

    //获取集合空间，不存在则创建
    //指定SDB连接，则使用指定的SDB连接
    public CollectionSpace getCollectionSpace(Sequoiadb sdb, String spaceName, boolean autoCreate) throws SequoiaAdapterException {
        if (!autoCreate)
            SequoiaAssertions.notFound(String.format("CollectionSpace '%s'",spaceName), !sdb.isCollectionSpaceExist(spaceName));
        CollectionSpace space = null;
        try{
            boolean isExist = sdb.isCollectionSpaceExist(spaceName);
            space = isExist ?
                    sdb.getCollectionSpace(spaceName) :
                    (autoCreate ? sdb.createCollectionSpace(spaceName) : null);
        }catch (BaseException e){
            throw SequoiaAdapterException.create(e);
        }
        return SequoiaAssertions.notNull("CollectionSpace", space);
    }

    //获取集合，不存在则创建
    //指定Space
    public DBCollection getCollection(CollectionSpace space, String collectionName, boolean autoCreate) throws SequoiaAdapterException {
        SequoiaAssertions.notNull("CollectionSpace", space);
        DBCollection collection = null;
        try {
            boolean isExist = space.isCollectionExist(collectionName);
            collection = isExist ?
                    space.getCollection(collectionName) :
                    (autoCreate ? space.createCollection(collectionName) : null);
        }catch (BaseException e){
            throw SequoiaAdapterException.create(e);
        }
        return SequoiaAssertions.notNull("Collection", collection);
    }
    //指定SDB连接和Space名称
//    public DBCollection getCollection(Sequoiadb sdb, String spaceName, String collectionName, boolean autoCreate) throws SequoiaAdapterException {
//        CollectionSpace space = getCollectionSpace(sdb, spaceName);
//        return getCollection(space, collectionName, autoCreate);
//    }

    public List<String> getSpaceNames(){
        List<String> list = new ArrayList<>();
        Sequoiadb sdb = null;
        try{
            sdb = getNewConnection();
            List<String> arr = sdb.getCollectionSpaceNames();
            if (arr != null)
                list.addAll(arr);
        } catch (SequoiaAdapterException e) {
            e.printStackTrace();
        } finally {
            if (sdb != null)
                sdb.releaseResource();
        }
        return list;
    }

    public List<String> getCollectionNames(){
        return getCollectionNames(getSpaceName());
    }

    public List<String> getCollectionNames(String spaceName){
        List<String> list = new ArrayList<>();
        Sequoiadb sdb = null;
        try{
            sdb = getNewConnection();
            if (sdb.isCollectionSpaceExist(spaceName)) {
                CollectionSpace space = sdb.getCollectionSpace(spaceName);
                if (space != null){
                    List<String> arr = space.getCollectionNames();
                    int s = arr.size();
                    for(int i = 0;i < s; i++)
                        arr.set(i,arr.get(i).replace(spaceName + ".", ""));
                    if (arr != null)
                        list.addAll(arr);
                }
            }
        } catch (SequoiaAdapterException e) {
            e.printStackTrace();
        } finally {
            if (sdb != null)
                sdb.releaseResource();
        }
        return list;
    }

    public int getUsedConnNum() throws SequoiaAdapterException {
        return datasource.getUsedConnNum();
    }

    public int getIdleConnNum() throws SequoiaAdapterException {
        return datasource.getIdleConnNum();
    }

    public void closeConnection(){
        if (null != connection.get()) {
//            connection.get().releaseResource();
            this.datasource.releaseConnection(connection.get());
            connection.remove();
        }
    }

    public void closeConnection(Sequoiadb conn){
        if (conn != null){
//            conn.releaseResource();
            this.datasource.releaseConnection(conn);
        }
    }

    public void closeDatasource(){
        if (datasource != null)
            datasource.close();
    }

    public SequoiadbDatasource getDatasource(){
        return  this.datasource;
    }
}
