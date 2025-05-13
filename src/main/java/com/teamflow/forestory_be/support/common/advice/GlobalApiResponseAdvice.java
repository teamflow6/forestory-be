package com.teamflow.forestory_be.support.common.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalApiResponseAdvice implements ResponseBodyAdvice {

    private static final String API_PREFIX = "/api/";

    private final ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response
    ) {
        String path = request.getURI().getPath();

        if (!path.startsWith(API_PREFIX)) {
            return body;
        }

        if (body instanceof String) {
            response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
            try {
                return objectMapper.writeValueAsString(new BaseApiResponse(path, body));
            } catch (Exception ex) {
                throw new RuntimeException("Failed to serialize String response", ex);
            }
        }

        return new BaseApiResponse(path, body);
    }
}
