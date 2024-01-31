package com.nginxtelegrm.NginxTelegramMessage.core.exception;

public class ControllerTelegramException extends RuntimeException {
    public ControllerTelegramException(String exception){
        super(exception);
    }
}
