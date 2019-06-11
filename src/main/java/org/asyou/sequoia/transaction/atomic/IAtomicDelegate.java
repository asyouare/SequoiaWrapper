package org.asyou.sequoia.transaction.atomic;

/**
 * Created by steven on 17/8/18.
 */
public interface IAtomicDelegate {
    boolean run() throws Throwable;
}
