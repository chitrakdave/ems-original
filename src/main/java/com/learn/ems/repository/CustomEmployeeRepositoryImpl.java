package com.learn.ems.repository;

import com.learn.ems.entity.EmployeeEntity;
import com.learn.ems.repository.input.SearchEmployee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class CustomEmployeeRepositoryImpl implements CustomEmployeeRepository {

    private final EntityManager entityManager;

    private static final String WHERE_CLAUSE = " where ";

    private static final String AND = " and ";

    @Override
    public Page<EmployeeEntity> searchEmployees(SearchEmployee input, int page, int size) {

        String query = "Select * from employee";

        if (StringUtils.isNotBlank(input.getName())) {
            query = query + addLikeQuery(query, "name", input.getName());
        }

        if (Objects.nonNull(input.getAge())) {
            query = query + addAndQuery(query, "age", input.getAge());
        }

        Query nativeQuery = entityManager.createNativeQuery(query, EmployeeEntity.class)
                .setMaxResults(size)
                .setFirstResult(page * size);
        List<EmployeeEntity> employees = nativeQuery.getResultList();

        Query countQuery = entityManager.createNativeQuery("Select count(*) from employee");
        long count = (long) countQuery.getSingleResult();

        return new PageImpl<>(employees, PageRequest.of(page, size), count);
    }


    private String addLikeQuery(String query, String key, String value) {
        return " where " + key+ " like '%" + value+ "%' ";
    }

    private String addAndQuery(String query, String key, int value) {
        String andQuery = "";

        if (!query.contains(WHERE_CLAUSE)) {
            andQuery = WHERE_CLAUSE;
        }

        else {
            andQuery = AND;
        }

        andQuery = andQuery + key + " = " + value;
        return andQuery;
    }

}
