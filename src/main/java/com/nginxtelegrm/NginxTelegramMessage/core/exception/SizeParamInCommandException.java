package com.nginxtelegrm.NginxTelegramMessage.core.exception;

public class SizeParamInCommandException extends RuntimeException {
    public SizeParamInCommandException(Integer inputParamCount, Integer expectedInputParamCount){
        super("Параметров принято: " + inputParamCount
                +
                "\nПараметров ожидается: " + expectedInputParamCount);
    }

    public SizeParamInCommandException(Integer inputParamCountMax){
        super("Параметров принято больше 1."
                +
                "\nПараметров ожидается от 0 до " + inputParamCountMax);
    }
}
