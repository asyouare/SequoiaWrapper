package org.asyou.sequoia.transaction.proxy;

/**
 * Created by steven on 17/8/21.
 */
public class RecursionCounter implements IRecursionCounter {
    private int before;
    private int method;
    private int after;
    private int beforeErrors;
    private int methodErrors;
    private int afterErrors;

    public RecursionCounter(){ init(); }

    private void init(){
        before = 0;
        method = 0;
        after = 0;
        beforeErrors = 0;
        methodErrors = 0;
        afterErrors = 0;
    }

    public int getAllErrors(){ return beforeErrors + afterErrors + methodErrors; }

    public void clearAll(){
        init();
    }

    public int increaseAndGetBefore(){
        before = before + 1;
        return before;
    }

    public int increaseAndGetAfter(){
        after = after + 1;
        return after;
    }

    public int increaseAndGetMethod(){
        method = method + 1;
        return method;
    }

    public int increaseAndGetBeforeErrors(){
        beforeErrors = beforeErrors + 1;
        return beforeErrors;
    }

    public int increaseAndGetAfterErrors(){
        afterErrors = afterErrors + 1;
        return afterErrors;
    }

    public int increaseAndGetMethodErrors(){
        methodErrors = methodErrors + 1;
        return methodErrors;
    }

    @Override
    public TransactionCounter clone() throws CloneNotSupportedException {
        return (TransactionCounter) super.clone();
    }

    public int getBefore() {
        return before;
    }

    public int getAfter() {
        return after;
    }

    public int getMethod() {
        return method;
    }

    public int getBeforeErrors() {
        return beforeErrors;
    }

    public int getAfterErrors() {
        return afterErrors;
    }

    public int getMethodErrors() {
        return methodErrors;
    }

    public String toString(){
        return String.format("{ before: %s, after: %s, method: %s } \r\n", before, after, method) +
                String.format("{ beforeErrors: %s, afterErrors: %s, methodErrors: %s }", beforeErrors, afterErrors,methodErrors);
    }
}
