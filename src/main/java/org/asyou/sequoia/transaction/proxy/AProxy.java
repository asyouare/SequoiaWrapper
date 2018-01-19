package org.asyou.sequoia.transaction.proxy;

import net.sf.cglib.proxy.MethodProxy;
import org.asyou.sequoia.dao.SequoiaAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by steven on 17/8/9.
 */
public abstract class AProxy<T> implements IProxy<T> {
//    private static Logger logger = Logger.getGlobal();

    //每个线程的计数器，KEY是线程ID
    protected static Map<String,TransactionCounter> transactionCounterMap;

    protected synchronized static TransactionCounter getCounter(String key){
        return transactionCounterMap.containsKey(key) ? transactionCounterMap.get(key) : createAndGetCounter(key);
    }

    protected synchronized static TransactionCounter createAndGetCounter(String key){
        if (!transactionCounterMap.containsKey(key))
            transactionCounterMap.put(key, new TransactionCounter());
        return transactionCounterMap.get(key);
    }

    protected synchronized static void removeCounter(String key){
        if (transactionCounterMap.containsKey(key))
            transactionCounterMap.remove(key);
    }

    static {
        transactionCounterMap = new HashMap<>();
    }

//    protected T target;
    protected SequoiaAdapter adapter;
    protected ITransactionDelegate delegate;
    protected T targetObject;

    protected abstract T execute(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable;

//    public abstract T create(Class<T> targetClass, ITransactionDelegate delegate);

    protected T invocation(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        T result = null;
        TransactionCounter counter = AProxy.getCounter("proxy:thread"+Thread.currentThread().getId()+":"+ adapter.getAdapterId());

        //todo before
        if (counter.increaseAndGetBefore() == 1)
            begin(counter);

        //todo method
        counter.increaseAndGetMethod();
        //如果methodProxy为空，则采用jdk的动态代理，否则采用cglib
        try {
            result = execute0(proxy, method, args, methodProxy, counter);
        }catch (Throwable e){
            rollback(counter);
            throw e;
        }

        //todo after
        counter.increaseAndGetAfter();
        if (counter.getAfter() == counter.getBefore()) {
            commit(counter);
//            System.out.println(String.format("%s:%s",adapter.getAdapterId(),counter.toString()));
//                logger.info("\r\n" + gson.getAdapterId() + counter.toString());
            removeCounter();
        }

        return result;
    }


    //事务开始
    protected void begin(TransactionCounter counter) throws Throwable {
        counter.increaseAndGetBegin();
        try {
            if (delegate != null) {
                boolean beginResult = delegate.begin();
                if (!beginResult) {
                    throw new Exception("transaction begin method is failed");
                }
            }
        } catch (Throwable e) {
            throw e;
        }
    }

    //事务执行过程，如果methodProxy为空则执行jdk的dynamic proxy，否则执行cglib intercept
    protected T execute0(Object proxy, Method method, Object[] args, MethodProxy methodProxy, TransactionCounter counter) throws Throwable {
        counter.increaseAndGetExecute();
        T result = null;
        result = execute(proxy,method,args,methodProxy);
//        counter.increaseAndGetExecuteErrors();
        return result;
    }

    //事务提交
    protected void commit(TransactionCounter counter) throws Throwable {
        if (counter.increaseAndGetCommit() == 1) {
            try {
                if (delegate != null) {
                    boolean commitResult = delegate.commit();
                    if (!commitResult) {
                        throw new Exception("transaction commit method is failed.");
                    }
                }
            } catch (Throwable e) {
                //todo counter increaseAndGetErrors()
                rollback(counter);
                throw e;
            }
        }
    }

    //事务回滚
    protected void rollback(TransactionCounter counter) throws Throwable {
        if (counter.increaseAndGetRollback() == 1) {
            try {
                if (delegate != null) {
                    boolean rollbackResult = delegate.rollback();
                    if (!rollbackResult) {
                        throw new Exception("transaction rollback method is failed.");
                    }
                }
            } catch (Throwable e) {
                throw e;
            }
        }
    }

    //删除计数器
    protected void removeCounter(){
        removeCounter("proxy:thread"+Thread.currentThread().getId()+":"+ adapter.getAdapterId());
    }
}
