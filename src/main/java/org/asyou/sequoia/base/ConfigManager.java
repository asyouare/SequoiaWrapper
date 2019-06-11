package org.asyou.sequoia.base;

import org.apache.commons.lang3.StringUtils;
import org.asyou.sequoia.dao.SequoiaHostManager;
import org.asyou.sequoia.exception.SequoiaAdapterException;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局配置管理器
 * Created by steven on 17/7/24.
 */
public class ConfigManager {

    private static Map<String, Config> map = new HashMap<>();

    public static int getSize(){
        return map != null ? map.size() : 0;
    }

    public static boolean addConfig(Config... configs) throws SequoiaAdapterException { return addConfig(true, configs); }

    public static boolean addConfig(boolean toCreateHost, Config... configs) throws SequoiaAdapterException {
        if (configs.length > 0) {
            Map<String, Config> cmap = new HashMap();
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
                cmap.put(config.getAdapterId(), config);
            }

            if (configs.length == cmap.size()){
                map.putAll(cmap);
                if (toCreateHost) {
                    SequoiaHostManager.addHost(configs);
                }
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
        StringBuilder strb = new StringBuilder();
        for(Map.Entry<String,Config> entry : map.entrySet()){
            strb.append(entry.getValue().toString());
            strb.append("\r\n");
        }
        return strb.toString();
    }
}
