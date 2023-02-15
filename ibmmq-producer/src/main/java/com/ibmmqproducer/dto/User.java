package com.ibmmqproducer.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true, chain = true)
public class User {
    public String name;
    public String id;
}