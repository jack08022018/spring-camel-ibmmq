package com.demo.repository.mssql;

import com.demo.repository.mssql.entity.RentalNewEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalNewRepository extends ReactiveCrudRepository<RentalNewEntity, Integer> {
}
