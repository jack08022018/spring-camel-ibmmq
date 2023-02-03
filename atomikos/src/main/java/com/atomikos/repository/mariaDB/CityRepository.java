package com.atomikos.repository.mariaDB;

import com.atomikos.repository.mariaDB.entity.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Integer> {

}
