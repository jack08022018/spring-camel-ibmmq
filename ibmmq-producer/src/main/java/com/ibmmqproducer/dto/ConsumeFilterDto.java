package com.ibmmqproducer.dto;

import lombok.Builder;
import lombok.experimental.Accessors;

@Builder
@Accessors(chain = true, fluent = true)
public class ConsumeFilterDto {
    public String adapterName;
    public String lmid;
}
