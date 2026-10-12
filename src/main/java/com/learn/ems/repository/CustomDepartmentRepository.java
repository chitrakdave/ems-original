package com.learn.ems.repository;


import com.learn.ems.entity.DepartmentEntity;
import com.learn.ems.repository.input.SearchDepartment;
import org.springframework.data.domain.Page;

public interface CustomDepartmentRepository {
    Page<DepartmentEntity> searchDepartments(SearchDepartment input, int page, int size);
}
