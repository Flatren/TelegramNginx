package com.nginxtelegrm.NginxTelegramMessage.controller.telegram;

import com.nginxtelegrm.NginxTelegramMessage.controller.telegram.role.CommonRole;
import com.nginxtelegrm.NginxTelegramMessage.core.abstracts.MessengerController;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.RuleChatToChat.RuleChatToChat;
import com.nginxtelegrm.NginxTelegramMessage.model.param.controllerParam.ruleChatToChat.AddUpdateRuleChatToChatParam;
import com.nginxtelegrm.NginxTelegramMessage.model.param.controllerParam.simple.OneStringParam;
import com.nginxtelegrm.NginxTelegramMessage.model.param.controllerParam.simple.ZeroOrOneStringParam;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.IntermediateCommand;
import com.nginxtelegrm.NginxTelegramMessage.model.pojo.message.MessageResponse;
import com.nginxtelegrm.NginxTelegramMessage.repository.RulesChatToChatRepository;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import com.nginxtelegrm.NginxTelegramMessage.util.MessageResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;

@Component
public class RuleChatToChatController extends MessengerController {

    @Autowired
    RulesChatToChatRepository rulesChatToChatRepository;


    public RuleChatToChatController(CommonRole commonRole) {
        super(commonRole);
    }

    @Override
    public List<String> getCommands(){
        return List.of("/add", "/delete", "/update", "/getAll");
    }

    private Optional<MessageResponse> add(AddUpdateRuleChatToChatParam addUpdateRuleChatToChatParam,
                                          MessageResponse messageResponse){
        rulesChatToChatRepository.insert(addUpdateRuleChatToChatParam.getRuleChatToChat());
        messageResponse.setText("Правило добавлено.");
        return Optional.of(messageResponse);
    }

    private Optional<MessageResponse> delete(OneStringParam name,
                                             MessageResponse messageResponse){
        rulesChatToChatRepository.delete(name.getValue());
        messageResponse.setText("Правило удалено.");
        return Optional.of(messageResponse);
    }

    private Optional<MessageResponse> update(AddUpdateRuleChatToChatParam addUpdateRuleChatToChatParam,
                                             MessageResponse messageResponse){
        rulesChatToChatRepository.update(addUpdateRuleChatToChatParam.getRuleChatToChat());
        messageResponse.setText("Правило обновлено.");
        return Optional.of(messageResponse);
    }

    private Optional<MessageResponse> get(ZeroOrOneStringParam name,
                                          MessageResponse messageResponse){
        if (name.isZeroParams()){
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Список правил:\n");
            rulesChatToChatRepository.get().forEach(item->
                    stringBuilder
                            .append(item.getNameRule())
                            .append("\n-----------"));
            messageResponse.setText(stringBuilder.toString());
        }else {
            List<RuleChatToChat> rules = rulesChatToChatRepository.get(name.getValue());
            if (rules.size() == 1)
                messageResponse.setText(JsonUtil.toJson(rules.get(0)));
            else{
                messageResponse.setText("Правило отсутствует.");
            }

        }

        return Optional.of(messageResponse);
    }

    @Override
    public Optional<MessageResponse> sendToTheMethod(IntermediateCommand intermediateCommand){
        MessageResponse messageResponse = MessageResponseUtil.build(intermediateCommand.getMessageRequest());
        return switch (intermediateCommand.getCommand()){
            case "/add" -> add(new AddUpdateRuleChatToChatParam(intermediateCommand.getParams()),
                    messageResponse);
            case "/delete" -> delete(new OneStringParam(intermediateCommand.getParams()),
                    messageResponse);
            case "/update" -> update(new AddUpdateRuleChatToChatParam(intermediateCommand.getParams()),
                    messageResponse);
            case "/getAll" -> get(new ZeroOrOneStringParam(intermediateCommand.getParams()),
                    messageResponse);
            default -> Optional.empty();
        };
    }
}
