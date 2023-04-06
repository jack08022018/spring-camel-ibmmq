package com.demo.controller;


import com.demo.dto.RequestDto;
import com.demo.dto.ResponseDto;
import com.demo.dto.UserData;
import com.demo.service.ApiService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ApiController {
    final ApiService apiService;
    final Gson gson;

    @GetMapping("/hello")
    public ResponseDto hello(@RequestBody RequestDto dto) {
        ExcuteApi<RequestDto, UserData> excuteApi = apiService::getInfo;
        return handle(excuteApi, dto);
    }

    public ResponseDto handle(ExcuteApi excute, RequestDto dto) {
        try {
            var lmid = RandomStringUtils.randomAlphabetic(6);
            dto.setLmid(lmid);
            log.info("{}: request={}", lmid, gson.toJson(dto));
            var data = excute.apply(dto);
            log.info("{}: response={}", lmid, gson.toJson(data));
            return ResponseDto.builder()
                    .requestId(dto.getRequestId())
                    .status("00")
                    .data(data)
                    .build();
        }catch (Exception e) {
            return ResponseDto.builder()
                    .requestId(dto.getRequestId())
                    .status("06")
                    .message(e.getMessage())
                    .build();
        }
    }

//    @GetMapping(value = "/test")
//    public <T> T test() {
//        var start = System.currentTimeMillis();
//        var dto = UserDto.builder()
//                .id("123")
//                .build();
//
//        HttpClient httpClient = HttpClient.create()
//                .responseTimeout(Duration.ofSeconds(2))
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 2000)
//                .option(ChannelOption.SO_KEEPALIVE, true)
//                .option(EpollChannelOption.TCP_KEEPIDLE, 300)
//                .option(EpollChannelOption.TCP_KEEPINTVL, 60)
//                .option(EpollChannelOption.TCP_KEEPCNT, 8);
//        WebClient webClient = WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .baseUrl("http://localhost:9199/ibmmq-consumeraaa")
//                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .build();
//
//        UserDto response = null;
//        try {
//            response = webClient.post()
//                    .uri("/api/test")
//                    .contentType(MediaType.APPLICATION_JSON)
////                    .bodyValue(dto)
//                    .exchangeToMono(s -> s.bodyToMono(UserDto.class))
//                    .block();
//        }catch (Exception e) {
//            System.out.println("exception:" + e.getMessage());
//            e.printStackTrace();
//            var end = System.currentTimeMillis();
//            System.out.println("aaa: " + (end - start));
//            return (T) e.getMessage();
//        }
//
//        var end = System.currentTimeMillis();
//        System.out.println("aaa: " + (end - start));
//        return (T) response;
//    }

}
