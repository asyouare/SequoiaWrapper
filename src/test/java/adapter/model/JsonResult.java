package adapter.model;

import java.lang.reflect.ParameterizedType;

/**
 * 统一返回数据
 * author 陈李 on 2017/6/17.
 */
public class JsonResult<T> implements java.io.Serializable {

    /**
     * 返回码
     */
    private String code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 是否成功
     */
    private boolean haveError;

    private String errMsg;

    public String getCode() {
        return code;
    }

    public JsonResult setCode(String code) {
        this.code = code;
        return this;
    }

    public T getData() {
        return data;
    }

    public JsonResult<T> setData(T data) {
//        System.out.println(data.getClass());
        this.data = data;
        return this;
    }

    public boolean getHaveError() {
        return haveError;
    }

    public JsonResult setHaveError(boolean haveError) {
        this.haveError = haveError;
        return this;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public JsonResult setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }

}