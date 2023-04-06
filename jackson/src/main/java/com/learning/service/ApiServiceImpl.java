package com.learning.service;

import com.learning.dto.CardInfoDto;
import com.learning.dto.PersonDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.dto.PersonInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final ObjectMapper customObjectMapper;

    @Override
    public <T> T jsonToString() throws JsonProcessingException {
        var dto = CardInfoDto.builder()
                .channel("omni")
                .refNumber("A1234")
                .fullName("jack")
                .build();

        return (T) customObjectMapper.writeValueAsString(dto);
    }

    @Override
    public <T> T stringToJson() throws JsonProcessingException {
        PersonInfo info = PersonInfo.builder()
                .given_name("John")
                .family_name("Doe")
                .build();
        String json = "{\"given_name\":\"John\",\"family_name\":\"Doe\"}";
        ObjectMapper mapper = new ObjectMapper();
//        var personDto = customObjectMapper.readValue(json, PersonDto.class);
        var personDto = customObjectMapper.convertValue(info, PersonDto.class);
        return (T) mapper.writeValueAsString(personDto);
    }

}
