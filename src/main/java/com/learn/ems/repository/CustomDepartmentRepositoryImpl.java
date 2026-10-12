package com.learn.ems.repository;

import com.learn.ems.entity.DepartmentEntity;
import com.learn.ems.entity.EmployeeEntity;
import com.learn.ems.repository.input.SearchDepartment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomDepartmentRepositoryImpl implements CustomDepartmentRepository {

    private final EntityManager entityManager;

    @Override
    public Page<DepartmentEntity> searchDepartments(SearchDepartment input, int page, int size){

        String query="select * from department";

        if(StringUtils.isNotBlank(input.getName())){
        query= query+ addLikeQuery(query, "name", input.getName());
    }

        Query nativeQuery = entityManager.createNativeQuery(query, DepartmentEntity.class)
                .setMaxResults(size)
                .setFirstResult(page * size);
        List<DepartmentEntity> departments = nativeQuery.getResultList();

        Query countQuery = entityManager.createNativeQuery("Select count(*) from department");
        long count = (long) countQuery.getSingleResult();

        return new PageImpl<>(departments, PageRequest.of(page, size), count);


}

private String addLikeQuery(String query, String key, String value){
        return " where "+ key+ " like '%"+ value+ "%' ";
    }
}


