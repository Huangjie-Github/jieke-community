package cn.jiekemaike.jiekecommunity.exception;

import org.springframework.stereotype.Repository;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }
    public String getMessage(){
        return message;
    }
}
