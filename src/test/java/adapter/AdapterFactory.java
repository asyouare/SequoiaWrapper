package adapter;

import org.asyou.sequoia.base.Config;
import org.asyou.sequoia.base.ConfigManager;
import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.dao.SequoiaAutoCreate;
import org.asyou.sequoia.exception.SequoiaAdapterException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 17/8/31.
 */
public class AdapterFactory {

    private static SequoiaAdapter adapterUser;
    private static SequoiaAdapter adapterOrder;

    public static SequoiaAdapter getAdapter() {
        if (null == adapterUser) {
            try {
                adapterUser = new SequoiaAdapter("user");
            } catch (SequoiaAdapterException e) {
                e.printStackTrace();
            }
        }
        return adapterUser;
    }

    public static SequoiaAdapter getAdapterLog() {
        if (null == adapterOrder) {
            try {
                adapterOrder = new SequoiaAdapter("order");
            } catch (SequoiaAdapterException e) {
                e.printStackTrace();
            }
        }
        return adapterOrder;
    }

    static{
        try {
            initConfig();
        } catch (SequoiaAdapterException e) {
            e.printStackTrace();
        }
    }

    public static void initConfig() throws SequoiaAdapterException {
        SequoiaAutoCreate.setAll(true);
        List<String> addrList = new ArrayList<String>();
        addrList.add("localhost:11910");
        ConfigManager.addConfig(
                new Config("user","cs", addrList),
                new Config("order","cs", addrList));
    }
}
