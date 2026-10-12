package com.learn.ems.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateDepartmentRequest {

    @NotBlank(message = "Department name is required")
    private String name;

}
