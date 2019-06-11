package org.asyou.sequoia.exception;

/**
 * Created by steven on 17/8/16.
 */
public class ProxyDelegateException extends SequoiaCustomException {
    public ProxyDelegateException(){
        super();
    }

    public ProxyDelegateException(String message) {
        super(message);
    }

    public static ProxyDelegateException create(Throwable e){
        return new ProxyDelegateException(SequoiaCustomException.getExceptionString(e));
    }

    public static ProxyDelegateException create(String message){
        return new ProxyDelegateException(message);
    }
}
