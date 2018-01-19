package org.asyou.sequoia.exception;

/**
 * Created by steven on 17/10/20.
 */
public abstract class SequoiaCustomException extends Exception {
    private static final String TEMPLATE = "%s - [%s(%s)]";

    public SequoiaCustomException(){
        super();
    }

    public SequoiaCustomException(String message) {
        super(message);
    }

    public static String getExceptionString(Throwable e){
        StackTraceElement[] stes = e.getStackTrace();
        StackTraceElement ste = (stes != null && stes.length > 0) ? stes[0] : null;
        if (ste != null)
            return String.format(TEMPLATE, e.getMessage(), ste.getFileName(), ste.getLineNumber());
        else
            return e.getMessage();
    }
}
