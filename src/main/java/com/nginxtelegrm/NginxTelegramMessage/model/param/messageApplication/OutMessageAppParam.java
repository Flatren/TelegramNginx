package com.nginxtelegrm.NginxTelegramMessage.model.param.messageApplication;

import com.nginxtelegrm.NginxTelegramMessage.model.pojo.LinkFileTelegram;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;

@Getter
@Setter
public class OutMessageAppParam {

    HashMap<String, String> params=new HashMap<>();
    URL url;
    LinkFileTelegram linkFileTelegram;


    public void setParam(String key, String value){
        params.put(key,value);
    }

    public void setParams(HashMap<String, String> params){
        this.params.putAll(params);
    }

    public void setUser(String user){
        params.put("user", user);
    }

    public void setChat(String chat){
        params.put("chat", chat);
    }

    public void setIdUser(String idUser){
        params.put("idUser", idUser);
    }

    public void setIdMessage(String idMessage){
        params.put("idMessage", idMessage);
    }

    public void setFile(String file){
        params.put("file", file);
    }
}
