package org.asyou.sequoia.exception;

/**
 * Created by steven on 17/10/23.
 */
public final class SequoiaAssertions {
    public static <T> T notNull(String name, T value) throws SequoiaAdapterException {
        if(value == null) {
            throw SequoiaAdapterException.create(name + " is null");
        } else {
            return value;
        }
    }

    public static void notFound(String name, boolean expression) throws SequoiaAdapterException {
        if(expression) {
            throw SequoiaAdapterException.create(name + " not found");
        }
    }
}
