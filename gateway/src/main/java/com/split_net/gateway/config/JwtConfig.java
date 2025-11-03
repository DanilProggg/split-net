package com.split_net.gateway.config;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
public class JwtConfig {
    @Value("${gateway.jwtToken}")
    private String jwtToken;

    private Map<String, Object> params;

    @PostConstruct
    public void init() throws Exception {
        // Базовая валидация JWT структуры
        if (jwtToken == null || jwtToken.trim().isEmpty()) {
            throw new IllegalArgumentException("JWT token is required");
        }

        String[] parts = jwtToken.split("\\.");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token format");
        }

        try {
            // Декодируем и парсим payload
            String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));
            ObjectMapper mapper = new ObjectMapper();
            params = mapper.readValue(payloadJson, new TypeReference<Map<String, Object>>() {});

            // Проверяем наличие subject
            if (!params.containsKey("sub") || params.get("sub") == null) {
                throw new IllegalArgumentException("JWT token missing 'sub' field");
            }

        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Base64 encoding in JWT token", e);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON in JWT payload", e);
        }
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
