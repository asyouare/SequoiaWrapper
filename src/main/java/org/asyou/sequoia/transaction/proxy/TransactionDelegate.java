package org.asyou.sequoia.transaction.proxy;

import org.asyou.sequoia.dao.SequoiaAdapter;
import org.asyou.sequoia.exception.SequoiaAdapterException;

/**
 * Created by steven on 17/8/21.
 */
public class TransactionDelegate implements ITransactionDelegate{
    private SequoiaAdapter adapter;

    public TransactionDelegate(SequoiaAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public boolean begin() {
//        System.out.println("Transaction begin: adapterId:" + adapter.getAdapterId());
        //todo optOutside a connection
        try {
            adapter.getHost().getLocalConnection().beginTransaction();
        } catch (SequoiaAdapterException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean commit() {
//        System.out.println("Transaction commit: adapterId:" + adapter.getAdapterId());
        //todo execute commit
        try {
            adapter.getHost().getLocalConnection().commit();
            adapter.getHost().closeConnection();
        } catch (SequoiaAdapterException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean rollback() {
//        System.out.println("Transaction rollback: adapterId:" + adapter.getAdapterId());
        //todo execute rollback
        try {
            adapter.getHost().getLocalConnection().rollback();
            adapter.getHost().closeConnection();
        } catch (SequoiaAdapterException e) {
            return false;
        }
        return true;
    }
}
