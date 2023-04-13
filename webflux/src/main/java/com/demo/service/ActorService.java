package com.demo.service;

import com.demo.repository.mariadb.entity.ActorEntity;
import reactor.core.publisher.Mono;

public interface ActorService {
    Mono getData();
    Mono<ActorEntity> saveData();
}
