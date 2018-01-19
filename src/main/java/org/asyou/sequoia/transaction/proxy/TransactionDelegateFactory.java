package org.asyou.sequoia.transaction.proxy;

/**
 * Created by steven on 17/8/24.
 */
public class TransactionDelegateFactory {


    private static ITransactionDelegate transactionDelegate;

    private synchronized static void setTransactionDelegate(ITransactionDelegate iTransactionDelegate){
        if (transactionDelegate == null)
            transactionDelegate = iTransactionDelegate;
    }

    public static ITransactionDelegate getTransactionDelegate(){
        return transactionDelegate;
    }
}
