package com.demo.controller;


import com.demo.dto.UserDto;
import com.google.protobuf.ServiceException;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Slf4j
@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class ConsumeController {
    @GetMapping(value = "/test")
    public <T> T test() {
        var dto = UserDto.builder()
                .id("123")
                .build();

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

        Mono<UserDto> result = null;
//        UserDto result = null;
        try {
            result = webClient.post()
                    .uri("/api/test")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
//                    .onStatus(HttpStatus::is5xxServerError, response -> {
//                        return Mono.error(new ServiceException("Server error", response.rawStatusCode()));
//                    })
                    .bodyToMono(UserDto.class);
//                    .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))
//                            .filter(throwable -> throwable instanceof ServiceException)
//                            .onRetryExhaustedThrow((retryBackoffSpec, retrySignal) -> {
//                                throw new ServiceException("External Service failed to process after max retries", HttpStatus.SERVICE_UNAVAILABLE.value());
//                            }));
//                    .subscribe();

//            result = webClient.post()
//                    .uri("/api/test")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .bodyValue(dto)
//                    .exchangeToMono(s -> s.bodyToMono(UserDto.class))
//                    .block();
        }catch (Exception e) {
            System.out.println("aaa:" + e.getMessage());
//            e.printStackTrace();
            var end = System.currentTimeMillis();
            return (T) e.getMessage();
        }

        return (T) result;
    }

//    @GetMapping(value = "/test")
//    public <T> T test() {
//        var dto = UserDto.builder()
//                .id("123")
//                .build();
//
////        Flux<UserDto> result = null;
//        Mono<UserDto> result = null;
////        UserDto result = null;
//        try {
//            result = webClient.post()
//                    .uri("/api/test")
//                    .body(Mono.just(dto), UserDto.class)
//                    .retrieve()
//                    .bodyToMono(UserDto.class)
//                    .timeout(Duration.ofMillis(10_000));
////            result = webClient.get()
////                    .uri("/api/test")
////                    .body(Flux.just(dto), UserDto.class)
////                    .retrieve()
////                    .bodyToFlux(UserDto.class)
////                    .timeout(Duration.ofMillis(10_000));
//        }catch (Exception e) {
//            System.out.println("aaa:" + e.getMessage());
////            e.printStackTrace();
//            var end = System.currentTimeMillis();
//            return (T) e.getMessage();
//        }
//
//        return (T) result;
//    }

}
