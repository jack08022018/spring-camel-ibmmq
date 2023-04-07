package com.demo.controller;


import com.demo.dto.RequestDto;
import com.demo.dto.ResponseDto;
import com.demo.dto.UserData;
import com.demo.dto.UserDto;
import com.demo.entity.ActorEntity;
import com.demo.service.ActorService;
import com.demo.service.ApiService;
import com.google.gson.Gson;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class WebfluxController {
    final ApiService apiService;
    final Gson gson;
    final ActorService actorService;
//    final WebClient webClient;

    HttpClient httpClient = HttpClient.create()
            .responseTimeout(Duration.ofSeconds(10))
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            .option(EpollChannelOption.TCP_KEEPCNT, 8);
    WebClient webClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:9199/ibmmq-consumer")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    @GetMapping("/webClient")
    public String webClient(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        webClient.post()
                .uri("/api/test")
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class)
                .flatMap(s -> {
                    System.out.println(LocalDateTime.now() + " RESPONSE1: " + s.getAddress());
                    return webClient.post()
                            .uri("/api/test")
                            .bodyValue(dto.getData())
                            .retrieve()
                            .bodyToMono(UserData.class);
                })
                .subscribe(s -> {
                    System.out.println(LocalDateTime.now() + " RESPONSE2: " + s.getAddress());
                });
        return "second";
//        var response1 = webClient.post()
//                .uri("/api/test")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(dto.getData())
////                .retrieve()
////                .bodyToMono(UserData.class)
////                .subscribeOn(Schedulers.boundedElastic())
//                .exchangeToMono(s -> s.bodyToMono(UserDto.class))
//                .block()
//                ;
//        dto.getData().setName("Nhung");
//        Mono<UserData> response2 = webClient.post()
//                .uri("/api/test")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(dto.getData())
//                .retrieve()
//                .bodyToMono(UserData.class)
//                .block()
//        response1.subscribe(s -> {
//            System.out.println(LocalDateTime.now() + " RESPONSE1: " + s.getAddress());
//        });
//        response2.subscribe(s -> {
//            System.out.println(LocalDateTime.now() + " response2: " + s.getAddress());
//        });
//        ModelMap result = new ModelMap();
//        result.put("response1", response1);
//        TimeUnit.SECONDS.sleep(4);
//        return ResponseDto.builder()
//                .requestId(dto.getRequestId())
//                .status("00")
//                .data(response)
//                .build();

//        ExcuteApi<RequestDto, UserData> excuteApi = apiService::getInfo;
//        return handle(excuteApi, dto);
    }

    @GetMapping("/webClient2")
    public Mono<UserData> webClient2(@RequestBody RequestDto<UserDto> dto) throws InterruptedException {
        Mono<UserData> first = webClient.post()
                .uri("/api/test")
                .bodyValue(dto.getData())
                .retrieve()
                .bodyToMono(UserData.class);

        Mono<UserData> second = first.flatMap(s -> {
            // Process first response here
            dto.getData().setName("Nhung");
            int a = 1/0;
            return webClient.post()
                    .uri("/api/test")
                    .bodyValue(dto.getData())
                    .retrieve()
                    .bodyToMono(UserData.class);
        });
        second.subscribe();

        return second
                .map(s -> s)
                .onErrorResume(ex -> {
                    throw new RuntimeException(ex);
                });
//                .onErrorResume(ex -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));

//        webClient.post()
//                .uri("/api/test")
//                .bodyValue(dto.getData())
//                .retrieve()
//                .bodyToMono(UserData.class)
//                .flatMap(s -> {
//                    System.out.println(LocalDateTime.now() + " RESPONSE1: " + s.getAddress());
//                    return webClient.post()
//                            .uri("/api/test")
//                            .bodyValue(dto.getData())
//                            .retrieve()
//                            .bodyToMono(UserData.class);
//                })
//                .subscribe(s -> {
//                    System.out.println(LocalDateTime.now() + " RESPONSE2: " + s.getAddress());
//                });
//        return Mono.empty();
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

    @GetMapping("/demo")
    public <T> T demo() {
//        Flux<Integer> ints = Flux.range(1, 6) //(1)
//                .map(i -> { // (2)
//                    if (i <= 3) {
//                        return i;
//                    }
//                    throw new RuntimeException("Got to 4");
//                });
//        ints.subscribe(i -> System.out.println(i), //(3)
//                error -> System.err.println("Error: " + error)); //(4)

//        public void onSubscribe(Subscription s);
//        public void onNext(T t);
//        public void onError(Throwable t);
//        public void onComplete();
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(
                        i -> System.out.println(i),
                        error -> System.err.println("Error " + error),
                        () -> System.out.println("Done"),
                        sub -> sub.request(10)
                );
//            .dispose();
//        Flux<Integer> ints = Flux.range(1, 4)
//                .map(i -> {
//                    return i;
////                    if (i <= 3) {
////                        return i;
////                    }
////                    throw new RuntimeException("Got to 4");
//                });
//        ints.subscribe(i -> System.out.println(i),
//                error -> System.err.println("Error: " + error),
//                () -> System.out.println("Done"),
//                sub -> sub.request(10));
//        System.out.println("bbb");
        return (T) "ints";
    }

    @GetMapping("/getData")
    public Mono<ActorEntity> getData() {
        return actorService.getActor();
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
