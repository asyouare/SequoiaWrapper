package org.asyou.sequoia.transaction.atomic;

import com.sequoiadb.base.Sequoiadb;
import org.asyou.sequoia.dao.SequoiaAdapter;

/**
 * Created by steven on 17/10/25.
 */
public class AtomicTransaction {

    public static void execute(SequoiaAdapter adapter, IAtomicDelegate atom) throws Throwable {
        Sequoiadb sdb = null;
        try {
            sdb = adapter.getHost().getLocalConnection();
            sdb.beginTransaction();
            boolean result = atom.run();
            if (result)
                sdb.commit();
            else
                sdb.rollback();
        }catch (Throwable te){
            if (null != sdb) {
                sdb.rollback();
            }
            throw te;
        }finally {
            if (null != sdb) {
                adapter.getHost().closeConnection();
            }
        }
    }
}
