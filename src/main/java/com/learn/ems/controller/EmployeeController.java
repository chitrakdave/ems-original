package com.learn.ems.controller;

import com.learn.ems.dto.request.CreateEmployeeRequest;
import com.learn.ems.dto.request.SearchEmployeeRequest;
import com.learn.ems.dto.request.UpdateEmployeeRequest;
import com.learn.ems.dto.response.EmployeeResponse;
import com.learn.ems.dto.response.PaginateResponse;
import com.learn.ems.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<EmployeeResponse> createEmployee(@RequestBody @Valid CreateEmployeeRequest request) {
        log.info("Create employee request: {}", request);
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/employee")
    public ResponseEntity<PaginateResponse<EmployeeResponse>> getEmployees(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "age", required = false) Integer age) {

        SearchEmployeeRequest request = new SearchEmployeeRequest();

        request.setName(name);
        request.setAge(age);
        request.setPage(Math.max(--page, 0));
        request.setSize(size);
        PaginateResponse<EmployeeResponse> employees = employeeService.getEmployees(request);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable long id) {
        log.info("Get employee by id: {}", id);
        EmployeeResponse response = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("employee/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable long id, @RequestBody UpdateEmployeeRequest request) {
        EmployeeResponse response = employeeService.updateEmployee(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("employee/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee with id " + id + " has been deleted successfully.");
    }

}
