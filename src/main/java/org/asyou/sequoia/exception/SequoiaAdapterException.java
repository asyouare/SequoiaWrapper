package org.asyou.sequoia.exception;

public class SequoiaAdapterException extends SequoiaCustomException{

    public SequoiaAdapterException(){
        super();
    }

    public SequoiaAdapterException(String message) {
        super(message);
    }

    public static SequoiaAdapterException create(Throwable e){
        return new SequoiaAdapterException(SequoiaCustomException.getExceptionString(e));
    }

    public static SequoiaAdapterException create(String message){
        return new SequoiaAdapterException(message);
    }
}
