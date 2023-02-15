package com.ibmmqproducer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestBodyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String json = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));

        // modify the request body as needed here
        System.out.println("xxx: " + json);
        json = """
                {
                    "name": "John",
                    "id": "2002"
                }""";

        ObjectMapper mapper = new ObjectMapper();
        MyRequestWrapper requestWrapper = new MyRequestWrapper(request, json);
        filterChain.doFilter(requestWrapper, servletResponse);
    }

    private static class MyRequestWrapper extends HttpServletRequestWrapper {

        private final String requestBody;

        public MyRequestWrapper(HttpServletRequest request, String requestBody) {
            super(request);
            this.requestBody = requestBody;
        }

        @Override
        public ServletInputStream getInputStream() throws IOException {
            return new RequestCachingInputStream(requestBody.getBytes(getCharacterEncoding()));
        }
    }
}