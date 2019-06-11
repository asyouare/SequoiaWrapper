package org.asyou.sequoia.transaction.proxy;

import org.asyou.sequoia.dao.SequoiaAdapter;

/**
 * Created by steven on 17/8/9.
 */
public interface IProxy<T> {
    T create(Class<T> targetClass, SequoiaAdapter adapter);
}
