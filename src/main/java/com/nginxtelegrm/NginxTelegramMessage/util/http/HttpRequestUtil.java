package com.nginxtelegrm.NginxTelegramMessage.util.http;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nginxtelegrm.NginxTelegramMessage.util.parse.JsonUtil;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HttpRequestUtil {

    private static HttpRequest buildPostHttpRequest(String url, String content){
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(content))
                .setHeader("Content-Type", "application/json")
                .build();
    }

    private static HttpRequest buildGetHttpRequest(String url){
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
    }

    @SneakyThrows
    private static String sendHttpRequest(HttpRequest httpRequest){
        HttpClient client = HttpClient.newHttpClient();
        return client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body();
    }

    public static <T> T sendRequest(String address, TypeReference<T> typeReference){
        HttpRequest httpRequest = buildPostHttpRequest(address, "");
        String jsonResponse = sendHttpRequest(httpRequest);
        return JsonUtil.parseJson(jsonResponse,typeReference);
    }

    public static <T> T sendRequest(String address, HashMap<String,String> params, TypeReference<T> typeReference){
        HttpRequest httpRequest = buildPostHttpRequest(address, JsonUtil.toJson(params));
        String jsonResponse = sendHttpRequest(httpRequest);
        return JsonUtil.parseJson(jsonResponse,typeReference);
    }

    public static <T> T sendRequest(String address, Object params, TypeReference<T> typeReference){
        HttpRequest httpRequest = buildPostHttpRequest(address, JsonUtil.toJson(params));
        String jsonResponse = sendHttpRequest(httpRequest);
        return JsonUtil.parseJson(jsonResponse,typeReference);
    }
}
