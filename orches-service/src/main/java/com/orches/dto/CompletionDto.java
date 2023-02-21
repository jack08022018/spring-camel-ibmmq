package com.orches.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = true, chain = true)
public class CompletionDto {
    public String workflowId;
    public String activityId;
    public String data;
}
