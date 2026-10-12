package com.learn.ems.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "department")
public class DepartmentEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "active")
    private boolean active;

}
