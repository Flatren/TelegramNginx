package com.nginxtelegrm.NginxTelegramMessage.model.param.controllerParam.ruleChatToChat;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.core.exception.SizeParamInCommandException;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.model.dto.RuleChatToChatDto;
import com.nginxtelegrm.NginxTelegramMessage.util.RuleChatToChatUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.YamlUtil;
import lombok.Getter;


import java.util.List;

@Getter
public class AddUpdateRuleChatToChatParam {
    RuleChatToChat ruleChatToChat;

    public AddUpdateRuleChatToChatParam(List<String> params){
        if (params.size() != 1){
            throw new SizeParamInCommandException(params.size(), 1);
        }
        RuleChatToChatDto intermediateRuleChatToChat
                = YamlUtil.load(params.get(0), new TypeReference<RuleChatToChatDto>() {});
        ruleChatToChat = RuleChatToChatUtil.map(intermediateRuleChatToChat);
    }
}
