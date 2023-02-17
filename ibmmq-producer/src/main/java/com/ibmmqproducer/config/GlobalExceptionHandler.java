package com.ibmmqproducer.config;

import com.ibmmqproducer.common.CommonUtils;
import com.ibmmqproducer.config.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Date;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends RequestBodyAdviceAdapter {
    final CommonUtils commonUtils;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
                   Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
//        if (body instanceof RequestDto<?>) {}
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> mainExceptionHandler(Exception e, HandlerMethod handlerMethod, HttpServletRequest servletRequest) {
        var REQUEST_ID = servletRequest.getAttribute("REQUEST_ID");
        log.error("\nREQUEST_ID={} mainExceptionHandler", REQUEST_ID, e);
        ErrorResponse response = ErrorResponse.builder()
                .timestamp(commonUtils.dateToString(new Date(), "yyyy-MM-dd HH:mm:ss"))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .path(servletRequest.getRequestURI())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
