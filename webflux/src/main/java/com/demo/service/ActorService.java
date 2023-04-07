package com.demo.service;

import com.demo.entity.ActorEntity;
import reactor.core.publisher.Mono;

public interface ActorService {
    Mono<ActorEntity> getActor();
}
