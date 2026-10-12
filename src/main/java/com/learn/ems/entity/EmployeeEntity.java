package com.learn.ems.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true, exclude = "department")
@Data
@NoArgsConstructor
@Entity
@Table(name = "employee")
@ToString(exclude = "department")
public class EmployeeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "active")
    private boolean active;

    @JoinColumn(name = "dept_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DepartmentEntity department;
}