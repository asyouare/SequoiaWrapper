package org.asyou.sequoia.exception;

public class SequoiaErrorCode {
    public static final SequoiaErrorCodeStruct ADAPTER_ID_EXISTED        = new SequoiaErrorCodeStruct(-001, "adapter id existed");
    public static final SequoiaErrorCodeStruct ADAPTER_ID_NOT_EXIST      = new SequoiaErrorCodeStruct(-002, "adapter id not exist");
    public static final SequoiaErrorCodeStruct DATABASE_NULL             = new SequoiaErrorCodeStruct(-003, "database null");
    public static final SequoiaErrorCodeStruct COLLECTION_NULL           = new SequoiaErrorCodeStruct(-005, "collection null");

    public static final SequoiaErrorCodeStruct FIND__ERROR               = new SequoiaErrorCodeStruct(-101, "find error");
    public static final SequoiaErrorCodeStruct COUNT__ERROR              = new SequoiaErrorCodeStruct(-102, "count error");
    public static final SequoiaErrorCodeStruct AGGREGATE__ERROR          = new SequoiaErrorCodeStruct(-103, "aggregate error");

    public static final SequoiaErrorCodeStruct INSERT_VALUE_NULL         = new SequoiaErrorCodeStruct(-111, "insert value null");
    public static final SequoiaErrorCodeStruct INSERT_ERROR              = new SequoiaErrorCodeStruct(-112, "insert error");

    public static final SequoiaErrorCodeStruct UPDATE_VALUE_NULL         = new SequoiaErrorCodeStruct(-121, "delete value null");
    public static final SequoiaErrorCodeStruct UPDATE_ERROR              = new SequoiaErrorCodeStruct(-122, "delete error");

    public static final SequoiaErrorCodeStruct DELETE_VALUE_NULL         = new SequoiaErrorCodeStruct(-131, "delete value null");
    public static final SequoiaErrorCodeStruct DELETE_ERROR              = new SequoiaErrorCodeStruct(-132, "delete error");

    public static final SequoiaErrorCodeStruct UNKNOWN_OPERATION         = new SequoiaErrorCodeStruct(-999, "unknown operation");     //未知操作
}
