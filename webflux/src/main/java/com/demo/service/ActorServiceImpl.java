package com.demo.service;

import com.demo.repository.mariadb.ActorRepository;
import com.demo.repository.mariadb.entity.ActorEntity;
import com.demo.repository.mssql.RentalNewRepository;
import com.demo.repository.mssql.entity.RentalNewEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorServiceImpl implements ActorService {
    final ActorRepository actorRepository;
    final RentalNewRepository rentalNewRepository;

    @Override
    public Mono getActor() {
//        return actorRepository.findById(1);
        Mono<ActorEntity> response1 = actorRepository.findById(1);
//        return rentalNewRepository.findById(152);
        Mono<RentalNewEntity> response2 = rentalNewRepository.findById(152);
//        return actorRepository.findById(1);
        return Mono.zip(response1, response2)
                .map(tuple -> {
                    ModelMap result = new ModelMap();
                    result.put("result1", tuple.getT1());
                    result.put("result2", tuple.getT2());
                    return result;
                });

//        return actorRepository.findById(1)
//                .doOnSuccess(s -> {
//                    System.out.println("SUCCESS");
//                })
//                .doOnError(s -> {
//                    try {
//                        throw s;
//                    } catch (Throwable e) {
//                        throw new RuntimeException(e);
//                    }
////                    s.getMessage();
//                });
//        actor.subscribe();
//        return actorRepository.findById(1);
    }
}
