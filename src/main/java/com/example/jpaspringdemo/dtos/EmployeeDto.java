package com.example.jpaspringdemo.dtos;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String fullName;
    private Integer salary;
    private Long departmentId;
}
