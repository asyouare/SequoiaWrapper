package org.asyou.sequoia.exception;

public class SequoiaExceptionStruct {
    private int code;
    private String message;

    public SequoiaExceptionStruct(int code, String message){
        this.code = code;
        this.message = message;
    }

    public String toString(){
        return this.code + " " + this.message;
    }

    public String from(SequoiaExceptionStruct exceptionStruct){
        return this.toString() + " from: " + exceptionStruct.toString();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
