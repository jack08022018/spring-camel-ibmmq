package com.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.StreamUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.nio.charset.Charset;

@Slf4j
//@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class RequestBodyFilter implements Filter {
    final ObjectMapper customObjectMapper;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var requestId = RandomStringUtils.randomAlphabetic(16);
        servletRequest.setAttribute("REQUEST_ID", requestId);

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        var body = StreamUtils.copyToString(request.getInputStream(), Charset.forName("UTF-8"));
        try {
            var requestDto = customObjectMapper.readValue(body, RequestDto.class);
            requestDto.requestId = requestId;
            body = customObjectMapper.writeValueAsString(requestDto);
        }catch (Exception e) {
            log.error("\nREQUEST_ID={} RequestBodyFilter msg={}", requestId, e.getMessage());
        }
        var requestWrapper = new MyRequestWrapper(request, body);
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