package org.asyou.sequoia.dao;

import org.apache.commons.lang3.StringUtils;
import org.asyou.sequoia.base.Config;
import org.asyou.sequoia.base.ConfigManager;
import org.asyou.sequoia.exception.SequoiaAdapterException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 17/8/29.
 */
public class SequoiaHostManager {

    private static Map<String,SequoiaHost> map = new HashMap<>();

    public static int getSize(){
        return map != null ? map.size() : 0;
    }

    public static SequoiaHost getSequoiaHost(String id){
        if (map.containsKey(id)) {
            return map.get(id);
        }
        return null;
    }

    public static boolean addHost(Config... configs) throws SequoiaAdapterException {
        if (configs.length > 0) {
            Map<String, SequoiaHost> cmap = new HashMap();
            for (Config config : configs) {
                if (config == null) {
                    throw new IllegalArgumentException(String.format("SequoiaConfig is null"));
                }
                if (StringUtils.isBlank(config.getAdapterId())) {
                    throw new IllegalArgumentException(String.format("SequoiaConfig adapter ID is null"));
                }
                if (map.containsKey(config.getAdapterId())) {
                    throw new IllegalArgumentException(String.format("SequoiaConfig adapter ID'%s' is already used", config.getAdapterId()));
                }
                SequoiaHost host = new SequoiaHost(config);
//                Sequoiadb sdb = null;
//                try {
//                    sdb = host.getNewConnection();
//                    if (!sdb.isCollectionSpaceExist(config.getSpaceName())) {
//                        throw SequoiaAdapterException.create(String.format("SequoiaDB collection space '%s' not found",config.getSpaceName()));
//                    }
//                }catch (InterruptedException e) {
//                    throw SequoiaAdapterException.create(e);
//                }finally {
//                    if (sdb != null)
//                        sdb.releaseResource();
//                }
                cmap.put(config.getAdapterId(), host);
            }

            if (configs.length == cmap.size()){
                map.putAll(cmap);
                return true;
            }else{
                throw new IllegalArgumentException(String.format("SequoiaConfig setting error"));
            }
        }
        return false;
    }

    public static boolean containsId(String adapterId){
        return map.containsKey(adapterId);
    }

    public static String getMapString(){
        return ConfigManager.getMapString();
    }
}
