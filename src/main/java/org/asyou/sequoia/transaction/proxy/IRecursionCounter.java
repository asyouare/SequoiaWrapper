package org.asyou.sequoia.transaction.proxy;

import java.io.Serializable;

/**
 * Created by steven on 17/8/21.
 */
public interface IRecursionCounter extends Cloneable, Serializable {
    int getBefore();
    int getMethod();
    int getAfter();
    int increaseAndGetBefore();
    int increaseAndGetMethod();
    int increaseAndGetAfter();
    String toString();
}
