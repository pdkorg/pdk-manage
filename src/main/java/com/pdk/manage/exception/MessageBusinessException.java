package com.pdk.manage.exception;

/**
 * 可抛出信息的业务异常
 * Created by hubo on 2015/8/19
 */
public class MessageBusinessException extends BusinessException {

    public MessageBusinessException() {
        super();
    }


    public MessageBusinessException(String message) {
        super(message);
    }


    public MessageBusinessException(String message, Throwable cause) {
        super(message, cause);
    }


    public MessageBusinessException(Throwable cause) {
        super(cause);
    }


    protected MessageBusinessException(String message, Throwable cause,
                                boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
