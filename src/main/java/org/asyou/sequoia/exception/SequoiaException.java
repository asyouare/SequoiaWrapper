package org.asyou.sequoia.exception;

public class SequoiaException {
    public static SequoiaExceptionStruct ADAPTER_ID_EXISTED        = new SequoiaExceptionStruct(-001, "adapter id existed");
    public static SequoiaExceptionStruct ADAPTER_ID_NOT_EXIST      = new SequoiaExceptionStruct(-002, "adapter id not exist");
    public static SequoiaExceptionStruct DATABASE_NULL             = new SequoiaExceptionStruct(-003, "database null");
    public static SequoiaExceptionStruct COLLECTION_NULL           = new SequoiaExceptionStruct(-005, "collection null");

    public static SequoiaExceptionStruct FIND__ERROR               = new SequoiaExceptionStruct(-101, "find error");
    public static SequoiaExceptionStruct COUNT__ERROR              = new SequoiaExceptionStruct(-102, "count error");
    public static SequoiaExceptionStruct AGGREGATE__ERROR          = new SequoiaExceptionStruct(-103, "aggregate error");

    public static SequoiaExceptionStruct INSERT_VALUE_NULL         = new SequoiaExceptionStruct(-111, "insert value null");
    public static SequoiaExceptionStruct INSERT_ERROR              = new SequoiaExceptionStruct(-112, "insert error");

    public static SequoiaExceptionStruct UPDATE_VALUE_NULL         = new SequoiaExceptionStruct(-121, "delete value null");
    public static SequoiaExceptionStruct UPDATE_ERROR              = new SequoiaExceptionStruct(-122, "delete error");

    public static SequoiaExceptionStruct DELETE_VALUE_NULL         = new SequoiaExceptionStruct(-131, "delete value null");
    public static SequoiaExceptionStruct DELETE_ERROR              = new SequoiaExceptionStruct(-132, "delete error");

    public static SequoiaExceptionStruct UNKNOWN_OPERATION         = new SequoiaExceptionStruct(-999, "unknown operation");     //未知操作
}
