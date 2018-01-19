package org.asyou.sequoia.transaction.atomic;

import org.asyou.sequoia.exception.SequoiaAdapterException;

/**
 * Created by steven on 17/8/18.
 */
public interface IAtomicDelegate {
    boolean run() throws Throwable;
}
