package com.split_net.gateway.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class GatewayConfig {
    @Value("${gateway.jwtToken}")
    private String jwtToken;

    private Map<String, Object> params;

    @PostConstruct
    public void init() throws Exception {
        // Достаем payload из JWT
        String[] parts = jwtToken.split("\\.");
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        // Парсим JSON в Map
        ObjectMapper mapper = new ObjectMapper();
        params = mapper.readValue(payloadJson, new TypeReference<Map<String, Object>>() {});
    }

    public String getSubject() {
        return (String) params.get("sub");
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public Object getParam(String key) {
        return params.get(key);
    }

    public String getParamAsString(String key) {
        return params.get(key) != null ? params.get(key).toString() : null;
    }

}
