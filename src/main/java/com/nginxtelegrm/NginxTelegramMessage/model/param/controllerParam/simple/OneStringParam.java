package com.nginxtelegrm.NginxTelegramMessage.model.param.controllerParam.simple;

import com.nginxtelegrm.NginxTelegramMessage.core.exception.SizeParamInCommandException;
import lombok.Getter;

import java.util.List;

@Getter
public class OneStringParam {
    String value;
    public OneStringParam(List<String> params){
        if (params.size() != 1){
            throw new SizeParamInCommandException(params.size(), 1);
        }
        value = params.get(0);
    }
}
