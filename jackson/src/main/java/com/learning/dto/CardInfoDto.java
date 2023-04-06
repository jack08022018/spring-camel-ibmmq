package com.learning.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfoDto {
    @JsonProperty("Channel")
    private String channel;

    @JsonProperty("RefNumber")
    private String refNumber;

    @JsonAlias({"firstName", "lastName"})
    private String fullName;
}
