package com.nginxtelegrm.NginxTelegramMessage.model.param.controllerParam.simple;

import com.nginxtelegrm.NginxTelegramMessage.core.exception.SizeParamInCommandException;
import lombok.Getter;

import java.util.List;

@Getter
public class ZeroOrOneStringParam {

    String value = "";

    boolean isZeroParams = true;

    public ZeroOrOneStringParam(List<String> params){
        if (params.size() > 1){
            throw new SizeParamInCommandException(1);
        }
        if (params.size() == 1) {
            value = params.get(0);
            isZeroParams = false;
        }
    }
}
