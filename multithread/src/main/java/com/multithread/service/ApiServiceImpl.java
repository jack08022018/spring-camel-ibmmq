package com.multithread.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.multithread.service.ApiService;
import com.multithread.service.RestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.concurrent.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    final RestService restService;
    final Executor taskExecutor;

    @Override
    public ModelMap getDataAsyncWithThreadPool() throws Exception {
        var result = new ModelMap();
        var getUser = CompletableFuture
                .runAsync(() -> {
                    result.put("user", restService.getUser());
                    System.out.println("AAA: " + Thread.currentThread().getName());
                }, taskExecutor)
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    result.put("user", e.getMessage());
                    return null;
                });
        var getClient = CompletableFuture
                .runAsync(() -> result.put("client", restService.getClient()), taskExecutor)
                .handle((res, ex) -> {
                    if (null != ex) {
                        log.error(ex.getMessage(), ex);
                        result.put("error", ex.getMessage());
                    }
                    return res;
                });
        getUser.get(6, TimeUnit.SECONDS);
        getClient.get(6, TimeUnit.SECONDS);
        return result;
    }

    @Override
    public ModelMap getDataAsyncNoThreadPool() throws Exception {
        var result = new ModelMap();
        var getUser = CompletableFuture
                .runAsync(() -> {
                    result.put("user", restService.getUser());
                    System.out.println("AAA: " + Thread.currentThread().getName());
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    result.put("user", e.getMessage());
                    return null;
                });
        var getClient = CompletableFuture
                .runAsync(() -> result.put("client", restService.getClient()))
                .handle((res, ex) -> {
                    if (null != ex) {
                        log.error(ex.getMessage(), ex);
                        result.put("error", ex.getMessage());
                    }
                    return res;
                });
        getUser.get(6, TimeUnit.SECONDS);
        getClient.get(6, TimeUnit.SECONDS);
        return result;
    }

    @Override
    public ModelMap getDataAsyncAllOf() throws Exception {
        var result = new ModelMap();
        var futureGetUser = CompletableFuture
                .runAsync(() -> result.put("user", restService.getUser()), taskExecutor);
        var futureGetClient = CompletableFuture
                .runAsync(() -> result.put("client", restService.getClient()), taskExecutor);
        CompletableFuture
            .allOf(futureGetUser, futureGetClient)
            .exceptionally(e -> {
                log.error(e.getMessage(), e);
                result.put("error", e.getMessage());
                return null;
            })
            .get(6, TimeUnit.SECONDS);
        return result;
    }

    @Override
    public ModelMap getDataAsyncAnnotation() throws Exception {
        var result = new ModelMap();
        var user = restService.getUserAsync();
        var client = restService.getClientAsync();
//        CompletableFuture<Void> saveUser = restService.saveUserAsync();
        CompletableFuture
                .allOf(user, client/*, saveUser*/)
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    result.put("error", e.getMessage());
                    return null;
                })
                .join();
        result.put("user", user.get(6, TimeUnit.SECONDS));
        result.put("client", client.get(6, TimeUnit.SECONDS));
//        saveUser.get(6, TimeUnit.SECONDS);
        return result;
    }
}
