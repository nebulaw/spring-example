package com.example.jpaspringdemo.dtos;

import com.example.jpaspringdemo.jpa.entities.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
  private Long id;
  private String fullName;
  private Integer salary;
  private Long departmentId;
}
