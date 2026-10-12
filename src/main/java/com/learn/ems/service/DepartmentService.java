package com.learn.ems.service;

import com.learn.ems.dto.request.CreateDepartmentRequest;
import com.learn.ems.dto.request.SearchDepartmentRequest;
import com.learn.ems.dto.request.SearchEmployeeRequest;
import com.learn.ems.dto.request.UpdateDepartmentRequest;
import com.learn.ems.dto.response.DepartmentResponse;
import com.learn.ems.dto.response.EmployeeResponse;
import com.learn.ems.dto.response.PaginateResponse;
import com.learn.ems.entity.DepartmentEntity;
import com.learn.ems.entity.EmployeeEntity;
import com.learn.ems.exception.DepartmentException;
import com.learn.ems.exception.Error;
import com.learn.ems.repository.DepartmentRepository;
import com.learn.ems.repository.input.SearchDepartment;
import com.learn.ems.repository.input.SearchEmployee;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse mapToResponse(DepartmentEntity department) {
        DepartmentResponse response = new DepartmentResponse();

        response.setId(department.getId());
        response.setName(department.getName());
        response.setActive(department.isActive());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());

        return response;
    }

    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        DepartmentEntity department = createDepartment(request.getName());
        return mapToResponse(department);
    }

    public DepartmentEntity createDepartment(String deptName) {
        DepartmentEntity department = new DepartmentEntity();
        department.setName(deptName);
        department.setActive(true);
        return departmentRepository.save(department);
    }

    public PaginateResponse<DepartmentResponse> getDepartments(SearchDepartmentRequest request) {
        SearchDepartment input= new SearchDepartment();
        input.setName(request.getName());

        Page<DepartmentEntity> pageData = departmentRepository.searchDepartments(input, request.getPage(), request.getSize());

        PaginateResponse<DepartmentResponse> response = new PaginateResponse<>();

        response.setData(pageData.getContent()
                .stream().map(this::mapToResponse).toList());

        response.setTotalCount(pageData.getTotalElements());
        response.setTotalPages(pageData.getTotalPages());
        response.setSize(pageData.getSize());
        response.setPage(pageData.getNumber() + 1);

        return response;

    }

    @Transactional
    public DepartmentEntity getDepartment(long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new DepartmentException("Department not found", Error.DEPT_NOT_FOUND));
    }

    public DepartmentResponse getDepartmentById(long id) {
        DepartmentEntity department = getDepartment(id);
        return mapToResponse(department);
    }

    public DepartmentResponse updateDepartment(long id, UpdateDepartmentRequest request) {
        DepartmentEntity department = getDepartment(id);

        department.setName(request.getName());
        departmentRepository.save(department);

        return mapToResponse(department);

    }

    public void deleteDepartment(long id) {
        if (!departmentRepository.existsById(id)) {
            throw new DepartmentException("Department not found", Error.DEPT_NOT_FOUND);
        }

        departmentRepository.deleteById(id);
        System.out.println("Department with id " + id + " has been deleted successfully");

    }

}
