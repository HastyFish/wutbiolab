package com.gooalgene.wutbiolab.exception;


import com.gooalgene.wutbiolab.response.common.ResultCode;

public class WutbiolabException extends RuntimeException {
    private Integer code;
    private ResultCode resultCode;
    public WutbiolabException(ResultCode resultCode) {
        super(resultCode.message);
        this.code = resultCode.code;
    }

    public WutbiolabException(String msg) {
        super(msg);
        //通用错误错误码
        this.code = 999;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

}
