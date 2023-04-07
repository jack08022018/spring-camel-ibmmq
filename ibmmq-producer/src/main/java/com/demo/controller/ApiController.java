package com.demo.controller;


import com.demo.dto.RequestDto;
import com.demo.dto.UserData;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.adapter.SenderService;
import com.demo.config.properties.TemporalProperties;
import com.demo.dto.UserDto;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiController {
    final JmsTemplate jmsTemplate;
    final ApplicationContext applicationContext;
    final SenderService senderService;
    final TemporalProperties temporalProperties;
    final ObjectMapper customObjectMapper;

//    public static void main(String[] args) {
//        var str = "{\"Data\":\"{\\\"LanguageID\\\":\\\"VI\\\",\\\"RefNumber\\\":\\\"EW0000001094\\\",\\\"CIFNo\\\":\\\"1690451\\\",\\\"omniID\\\":\\\"142\\\",\\\"MobileNo\\\":\\\"0905505048\\\",\\\"FromDate\\\":\\\"20230101\\\",\\\"ToDate\\\":\\\"20231201\\\",\\\"Channel\\\":\\\"MCS\\\",\\\"CardToken\\\":\\\"3490441247\\\"}\",\"FunctionName\":\"CMSSTBMin\",\"RequestDateTime\":\"2018-06-19T12:12:02Z\",\"RequestID\":\"C28128EA-0703-44FD-8C84-04EDB572E140\"}";
//        System.out.println(str);
//    }
//
//    @Value("${queues.queueDev1}")
//    private String queueDev1;
//
//    @Value("${queues.queueDev2}")
//    private String queueDev2;
//
//    @GetMapping(value = "/modify")
//    public <T> T modify(@RequestBody RequestDto<UserDto> req) throws JsonProcessingException {
////        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
////        for(String beanName : allBeanNames) {
////            System.out.println(beanName);
////        }
////        return (T) customObjectMapper.writeValueAsString(req);
//        var data = """
//                <EBk>
//                <BODY>aa</BODY>
//                </EBk>
//                """;
//
//        return (T) data.lines().collect(Collectors.joining(""));
//    }

    @GetMapping("/webClient")
    public <T> T webClient(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
//        Map<String, String> map = new HashMap<>();
//        map.put("profile", "Developer");
//        map.put("tech", "Java");

        String url = "http://localhost:9199/ibmmq-consumer/api/test";
        UserData response1 = restTemplate.getForObject(url, UserData.class, dto.getData());
        System.out.println(LocalDateTime.now() + " RESPONSE1: " + response1.getAddress());

//        response2.subscribe(s -> {
//            System.out.println(LocalDateTime.now() + " response2: " + s.getAddress());
//        });
        System.out.println("bbb");
        return (T) response1;
    }

    @GetMapping(value = "/test")
    public <T> T test() {
        var start = System.currentTimeMillis();
        var dto = UserDto.builder()
                .id("123")
                .build();

        HttpClient httpClient = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(2))
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(EpollChannelOption.TCP_KEEPIDLE, 300)
                .option(EpollChannelOption.TCP_KEEPINTVL, 60)
                .option(EpollChannelOption.TCP_KEEPCNT, 8);
        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:9199/ibmmq-consumeraaa")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        UserDto response = null;
        try {
            response = webClient.post()
                    .uri("/api/test")
                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(dto)
                    .exchangeToMono(s -> s.bodyToMono(UserDto.class))
                    .block();
        }catch (Exception e) {
            System.out.println("exception:" + e.getMessage());
            e.printStackTrace();
            var end = System.currentTimeMillis();
            System.out.println("aaa: " + (end - start));
            return (T) e.getMessage();
        }

        var end = System.currentTimeMillis();
        System.out.println("aaa: " + (end - start));
        return (T) response;
    }

//    @GetMapping("send2")
//    String send2(@RequestParam String adapter) {
//        String message = "Hello World!";
//        jmsTemplate.convertAndSend(queueDev2, message, messagePostProcessor -> {
//            messagePostProcessor.setStringProperty("ADAPTER", adapter);
//            return messagePostProcessor;
//        });
//        return "OK";
//    }

}
