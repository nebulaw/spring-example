package com.example.jpaspringdemo.dtos.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmployeeProjectionDto {
    private String fullName;
    private Integer salary;
}
