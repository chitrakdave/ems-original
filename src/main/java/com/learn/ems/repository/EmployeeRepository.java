package com.learn.ems.repository;

import com.learn.ems.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long>, CustomEmployeeRepository {
    List<EmployeeEntity> findByName(String name);

}
