package com.atomikos.repository.mssql;

import com.atomikos.repository.mssql.entity.RentalNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalNewRepository extends JpaRepository<RentalNewEntity, Integer> {
}
