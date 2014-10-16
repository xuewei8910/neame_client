package com.xuewei8910.neame_client.web;

import java.security.PublicKey;

/**
 * Created by Wei on 2014/10/15.
 */
public class SecuredException extends RuntimeException {
    public static final int AUTHENTICATE_FAILED = 1;

    private int errorCode;

    public SecuredException() {
        super();
    }

    public SecuredException(int errorCode){
        super();
        this.errorCode = errorCode;
    }

    public SecuredException(String detailMessage) {
        super(detailMessage);
    }

    public SecuredException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SecuredException(Throwable throwable) {
        super(throwable);
    }

    public int getErrorCode(){
        return this.errorCode;
    }
}
