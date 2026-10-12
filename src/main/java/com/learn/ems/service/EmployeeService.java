package com.learn.ems.service;

import com.learn.ems.dto.request.CreateEmployeeRequest;
import com.learn.ems.dto.request.SearchEmployeeRequest;
import com.learn.ems.dto.request.UpdateEmployeeRequest;
import com.learn.ems.dto.response.EmployeeResponse;
import com.learn.ems.dto.response.PaginateResponse;
import com.learn.ems.entity.DepartmentEntity;
import com.learn.ems.entity.EmployeeEntity;
import com.learn.ems.exception.DepartmentException;
import com.learn.ems.exception.EmployeeException;
import com.learn.ems.exception.Error;
import com.learn.ems.repository.EmployeeRepository;
import com.learn.ems.repository.input.SearchEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;

    private EmployeeResponse mapToResponse(EmployeeEntity employee) {

        EmployeeResponse response = new EmployeeResponse();

        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setAge(employee.getAge());

        response.setActive(employee.isActive());
        response.setCreatedAt(employee.getCreatedAt());
        response.setUpdatedAt(employee.getUpdatedAt());

        response.setDepartment(departmentService.mapToResponse(employee.getDepartment()));

        return response;
    }

    @Transactional
    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        checkIfUserAlreadyExists(request.getName());

        DepartmentEntity department = getDepartment(request);
        EmployeeEntity employee = new EmployeeEntity();

        employee.setName(request.getName());
        employee.setAge(request.getAge());
        employee.setDepartment(department);
        employee.setActive(true);

        employeeRepository.save(employee);
        return mapToResponse(employee);
    }

    private DepartmentEntity getDepartment(CreateEmployeeRequest request) {
        try {
            return departmentService.getDepartment(request.getDeptId());
        } catch (DepartmentException e) {
            log.error("Error while fetching department", e);
            return departmentService.createDepartment(request.getDeptName());
        }
    }

    private void checkIfUserAlreadyExists(String name) {
        List<EmployeeEntity> employees = employeeRepository.findByName(name);
        if (employees.size() > 0) {
            throw new EmployeeException("Employee already exist with this name", Error.EMPLOYEE_ALREADY_EXISTS);
        }
    }

    public PaginateResponse<EmployeeResponse> getEmployees(SearchEmployeeRequest request) {
        SearchEmployee input = new SearchEmployee();
        input.setName(request.getName());
        input.setAge(request.getAge());

        Page<EmployeeEntity> pageData = employeeRepository.searchEmployees(input, request.getPage(), request.getSize());

        PaginateResponse<EmployeeResponse> response = new PaginateResponse<>();
        response.setData(pageData.getContent().stream().map(this::mapToResponse).toList());
        response.setTotalCount(pageData.getTotalElements());
        response.setTotalPages(pageData.getTotalPages());
        response.setSize(pageData.getSize());
        response.setPage(pageData.getNumber() + 1);

        return response;
    }

    public EmployeeResponse getEmployeeById(long id) {
        EmployeeEntity employee = getEmployee(id);
        return mapToResponse(employee);
    }

    public EmployeeResponse updateEmployee(long id, UpdateEmployeeRequest request) {
        EmployeeEntity employee = getEmployee(id);
        employee.setName(request.getName());
        employee.setAge(request.getAge());

        employeeRepository.save(employee);

        return mapToResponse(employee);
    }

    // we created this method as it is used twice. it is used in getEmployeeByID and updateEmployee methods.
    private EmployeeEntity getEmployee(long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeException("Employee not found", Error.EMPLOYEE_NOT_FOUND));
    }

    public void deleteEmployee(long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeException("Employee not found", Error.EMPLOYEE_NOT_FOUND);
        }

        employeeRepository.deleteById(id);
        System.out.println("Employee with " + id + " has been deleted successfully");
    }
}
