package com.demo.repository.mssql;

import com.demo.repository.mssql.entity.RentalNewEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//@Qualifier("mssqlDatabaseClient")
public interface RentalNewRepository extends ReactiveCrudRepository<RentalNewEntity, Integer> {
}
