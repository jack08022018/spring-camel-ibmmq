package com.learning.repository.mariaDB;

import com.learning.repository.mariaDB.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {
    boolean existsById(Integer id);

}
