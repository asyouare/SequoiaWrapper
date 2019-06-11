package org.asyou.sequoia.transaction.proxy;

/**
 * Created by steven on 17/8/17.
 */
public class TransactionCounter implements IRecursionCounter {
    private int before;
    private int method;
    private int after;

    private int begin;
    private int execute;
    private int commit;
    private int rollback;


    public TransactionCounter(){ init(); }

    private void init(){
        before = 0;
        method = 0;
        after = 0;
        begin = 0;
        execute = 0;
        commit = 0;
        rollback = 0;

    }


    public void clearAll(){ init(); }

    public int increaseAndGetBegin(){ return ++begin; }

    public int increaseAndGetCommit(){ return ++commit; }

    public int increaseAndGetRollback(){ return ++rollback; }

    public int increaseAndGetExecute(){ return ++execute; }





    public int getBegin() { return begin; }

    public int getCommit() { return commit; }

    public int getRollback() { return rollback; }

    public int getExecute() { return execute; }





    @Override
    public String toString(){
        return String.format("{ before: %s, method: %s, after: %s, ",before,method,after) +
                String.format("begin: %s, execute: %s, commit: %s, rollback: %s } ", begin, execute, commit, rollback);
    }

    @Override
    public TransactionCounter clone() throws CloneNotSupportedException { return (TransactionCounter) super.clone(); }

    @Override
    public int getBefore() { return before; }

    @Override
    public int getMethod(){ return method; }

    @Override
    public int getAfter() { return after; }

    @Override
    public int increaseAndGetBefore() { return ++before; }

    @Override
    public int increaseAndGetMethod(){ return ++method; }

    @Override
    public int increaseAndGetAfter() { return ++after; }
}
