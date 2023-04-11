package com.demo.service;

import com.demo.repository.mariadb.entity.ActorEntity;
import org.springframework.ui.ModelMap;
import reactor.core.publisher.Mono;

public interface ActorService {
    Mono getActor();
}
