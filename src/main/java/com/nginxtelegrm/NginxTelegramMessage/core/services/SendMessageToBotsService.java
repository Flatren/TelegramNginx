package com.nginxtelegrm.NginxTelegramMessage.core.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.Command;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.response.ResponseMessage;
import com.nginxtelegrm.NginxTelegramMessage.core.modeles.rule.Rule;
import com.nginxtelegrm.NginxTelegramMessage.core.utilites.Mappers;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class SendMessageToBotsService {

    private HttpRequest buildPostHttpRequest(String url, String content){
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .setHeader("Content-Type", "application/json")
                .build();
    }

    private HttpRequest buildGetHttpRequest(String url){
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    @SneakyThrows
    private String sendHttpRequest(HttpRequest httpRequest){
        HttpClient client = HttpClient.newHttpClient();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
    }

    public String getConfig(String url){
        return sendHttpRequest(buildGetHttpRequest(url));
    }

    @SneakyThrows
    public void sendCommand(Command command, Rule rule) {
        String jsonTextRequest = new ObjectMapper().writeValueAsString(command.getParams());
        HttpRequest httpRequest = buildPostHttpRequest(rule.getToAddress() + command.getCommand(), jsonTextRequest);
        ResponseMessage result = Mappers.getObjectJson(sendHttpRequest(httpRequest), ResponseMessage.class);
    }

    @SneakyThrows
    public void sendDefault(String text, Rule rule){
        HttpRequest httpRequest =
                buildPostHttpRequest(rule.getToAddress() +rule.getSimpleMessages().getTo(),
                        text);
        ResponseMessage result = Mappers.getObjectJson(sendHttpRequest(httpRequest), ResponseMessage.class);
    }
}
