package com.ibmmqproducer.dto;

import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
public class RequestDto<T> {
    public String requestId;
    public T body;
}
