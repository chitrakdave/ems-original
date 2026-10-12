package com.learn.ems.controller;

import com.learn.ems.dto.request.*;
import com.learn.ems.dto.response.DepartmentResponse;
import com.learn.ems.dto.response.EmployeeResponse;
import com.learn.ems.dto.response.PaginateResponse;
import com.learn.ems.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping("/department")
    public ResponseEntity<DepartmentResponse> createDepartment(@RequestBody @Valid CreateDepartmentRequest request) {
        DepartmentResponse response = departmentService.createDepartment(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/department")
    public ResponseEntity<PaginateResponse<DepartmentResponse>> getDepartments(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "name", required = false) String name){

            SearchDepartmentRequest request= new SearchDepartmentRequest();

            request.setName(name);
            request.setPage(Math.max(--page,0));
            request.setSize(size);

            PaginateResponse<DepartmentResponse> departments= departmentService.getDepartments(request);

            return ResponseEntity.ok(departments);

    }

    @GetMapping("/department/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable long id){
        log.info("Get dept by id: {}", id);
        DepartmentResponse response= departmentService.getDepartmentById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(@PathVariable long id, @RequestBody UpdateDepartmentRequest request){
        DepartmentResponse response= departmentService.updateDepartment(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/department/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable long id){
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Dept with id "+id+" has been deleted successfully");
    }

}
