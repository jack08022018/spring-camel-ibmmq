package com.demo.service;

import com.demo.entity.ActorEntity;
import com.demo.repository.ActorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    final ActorRepository actorRepository;

    @Override
    public Mono<ActorEntity> getActor() {
        var actor = actorRepository.findById(1);
        actor.subscribe();
        return actorRepository.findById(1);
    }
}
