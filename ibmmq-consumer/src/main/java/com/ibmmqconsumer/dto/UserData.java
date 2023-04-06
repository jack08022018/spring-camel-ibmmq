package com.ibmmqconsumer.dto;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private String name;
    private String address;
}

