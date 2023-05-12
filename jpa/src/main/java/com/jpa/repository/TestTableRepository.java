package com.jpa.repository;

import com.jpa.entity.TestTableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestTableRepository extends JpaRepository<TestTableEntity, Integer> {
//    @Query("SELECT * FROM test_table WHERE rental_id = :id")
//    TestTableEntity findById(@Param("id") Integer id);
}
