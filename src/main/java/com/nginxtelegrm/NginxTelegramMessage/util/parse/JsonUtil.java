package com.nginxtelegrm.NginxTelegramMessage.util.parse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

public final class JsonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

    public static String toJson(Object o) {
        ObjectMapper s = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            s.writeValue(writer, o);
            return writer.toString();
        } catch (Exception e) {
            LOG.info(e.getMessage(), e);
        }
        return "";
    }

    public static <T> T parseJson(String response, TypeReference<T> typeReference) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, typeReference);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            LOG.error(response);
        }
        return null;
    }
}