package com.camelmultidb.repository.mariaDB;

import com.camelmultidb.repository.mariaDB.entity.RentalNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalMariaRepository extends JpaRepository<RentalNewEntity, Integer> {
}
