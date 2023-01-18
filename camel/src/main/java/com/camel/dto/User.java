package com.camel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Accessors(fluent = true, chain = true)
public class User {
    public String name;
}