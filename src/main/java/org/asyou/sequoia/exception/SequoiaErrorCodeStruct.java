package org.asyou.sequoia.exception;

public class SequoiaErrorCodeStruct {
    private int code;
    private String message;

    public SequoiaErrorCodeStruct(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString(){
        return this.code + " " + this.message;
    }

    public String from(SequoiaErrorCodeStruct exceptionStruct){
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
