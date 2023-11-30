package com.example.jpaspringdemo.services;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.EmployeeDto;

public interface IEmployeeService {

  ApiResponse addEmployee(Long departmentId, DataDto<EmployeeDto> dataDto);

  ApiResponse getEmployee(Long id);

  ApiResponse getAllEmployees();

  ApiResponse getAllEmployeesByDepartmentId(Long departmentId);

  ApiResponse updateEmployee(Long id, DataDto<EmployeeDto> dataDto);

  ApiResponse deleteEmployee(Long id);

}
