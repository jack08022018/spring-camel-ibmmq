package com.demo.repository.mariadb;

import com.demo.repository.mariadb.entity.ActorEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Qualifier("mariadbDatabaseClient")
public interface ActorRepository extends ReactiveCrudRepository<ActorEntity, Integer> {
//    @Query("SELECT * FROM actor WHERE id = :id")
//    Flux<ActorEntity> findById(@Param("id") Long id);
}
