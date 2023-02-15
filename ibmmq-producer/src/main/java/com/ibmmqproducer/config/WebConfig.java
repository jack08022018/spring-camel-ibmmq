package com.ibmmqproducer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomRequestBodyArgumentResolver());
    }

    static class CustomRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

        @Override
        public boolean supportsParameter(MethodParameter parameter) {
            return parameter.hasParameterAnnotation(RequestBody.class);
        }

        @Override
        public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

            HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
            String json = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));

            // modify the request body as needed here

            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, parameter.getParameterType());
        }
    }
}