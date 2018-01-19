package org.asyou.sequoia.base;

import com.google.gson.Gson;
import com.sequoiadb.datasource.ConnectStrategy;
import com.sequoiadb.datasource.DatasourceOptions;
import com.sequoiadb.net.ConfigOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 17/7/24.
 */
public class Config implements Serializable, Cloneable{
    private String adapterId;
    private String spaceName;
    private String userName;
    private String password;
    private List<String> addressList;
    private ConfigOptions configOptions;
    private DatasourceOptions datasourceOptions;

    public String getAdapterId(){ return adapterId; }

    public String getSpaceName(){ return this.spaceName; }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public ConfigOptions getConfigOptions() {
        return configOptions;
    }

    public DatasourceOptions getDatasourceOptions() {
        return datasourceOptions;
    }

    private void default_init(String adapterId, String spaceName, String userName, String password,
                              List<String> addressList,
                              ConfigOptions configOptions,
                              DatasourceOptions datasourceOptions){
        this.adapterId = adapterId;
        this.spaceName = spaceName;
        this.userName = userName;
        this.password = password;

        this.addressList = new ArrayList<String>();
        this.addressList.addAll(addressList);

        this.configOptions = new ConfigOptions();
        this.configOptions.setConnectTimeout(configOptions.getConnectTimeout());
        this.configOptions.setMaxAutoConnectRetryTime(configOptions.getMaxAutoConnectRetryTime());

        try {
            this.datasourceOptions = (DatasourceOptions) datasourceOptions.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public Config(String adapterId, String spaceName, String userName, String password,
                  ArrayList<String> addressList,
                  ConfigOptions configOptions,
                  DatasourceOptions datasourceOptions) {
        default_init(adapterId, spaceName,userName,password,addressList,configOptions,datasourceOptions);
    }

    public Config(String adapterId, String spaceName, List<String> addressList){
        List<String> addrList = new ArrayList<String>();
        addrList.addAll(addressList);
        default_init(adapterId, spaceName,"","",
                addrList,default_configOptions(),default_datasourceOptions());
    }

    public Config(String adapterId, String spaceName){
        // 提供coord节点
        List<String> addrList = new ArrayList<String>();
        addrList.add("localhost:11810");
        addrList.add("localhost:11811");
        addrList.add("localhost:11812");
        default_init(adapterId, spaceName,"","",
                addrList,default_configOptions(),default_datasourceOptions());
    }

    public Config(){
        // 提供coord节点
        List<String> addrList = new ArrayList<String>();
        addrList.add("localhost:11810");
        addrList.add("localhost:11811");
        addrList.add("localhost:11812");
        default_init("sequoiadb1", "space1","","",
                addrList,default_configOptions(),default_datasourceOptions());
    }

    private ConfigOptions default_configOptions(){
        // 设置网络参数
        ConfigOptions options = new ConfigOptions();
        options.setConnectTimeout(2 * 60 * 1000);                      // 建连超时时间为500ms。
        options.setMaxAutoConnectRetryTime(0);               // 建连失败后重试时间为0ms。
        return options;
    }

    private DatasourceOptions default_datasourceOptions(){
        DatasourceOptions options = new DatasourceOptions();
        // 设置连接池参数
        options.setMaxCount(1000);                            // 连接池最多能提供500个连接。
        options.setDeltaIncCount(20);                        // 每次增加20个连接。
        options.setMaxIdleCount(200);                         // 连接池空闲时，保留20个连接。
        options.setKeepAliveTimeout(0);                      // 池中空闲连接存活时间。单位:毫秒。
        // 0表示不关心连接隔多长时间没有收发消息。

        options.setCheckInterval(60 * 1000);                 // 每隔60秒将连接池中多于
        // MaxIdleCount限定的空闲连接关闭，
        // 并将存活时间过长（连接已停止收发
        // 超过keepAliveTimeout时间）的连接关闭。

        options.setSyncCoordInterval(0);                     // 向catalog同步coord地址的周期。单位:毫秒。
        // 0表示不同步。

        options.setValidateConnection(true);                // 连接出池时，是否检测连接的可用性，默认不检测。
        options.setConnectStrategy(ConnectStrategy.BALANCE); // 默认使用coord地址负载均衡的策略获取连接。

        return options;
    }

    @Override
    public Config clone() throws CloneNotSupportedException {
        return (Config) super.clone();
    }

    public String toString(){
        return new Gson().toJson(this);
    }

}
