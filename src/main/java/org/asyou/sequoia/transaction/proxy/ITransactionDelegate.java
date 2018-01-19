package org.asyou.sequoia.transaction.proxy;

/**
 * Created by steven on 17/8/21.
 */
public interface ITransactionDelegate {
    boolean begin();
    boolean commit();
    boolean rollback();
}
