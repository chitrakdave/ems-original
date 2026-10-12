package com.learn.ems.repository;

import com.learn.ems.entity.EmployeeEntity;
import com.learn.ems.repository.input.SearchEmployee;
import org.springframework.data.domain.Page;

public interface CustomEmployeeRepository {
    Page<EmployeeEntity> searchEmployees(SearchEmployee input, int page, int size);
}
