package com.example.jpaspringdemo.services;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.EmployeeDto;

public interface IEmployeeService {

    ApiResponse addEmployee(Long departmentId, DataDto<EmployeeDto> dataDto);

    ApiResponse getEmployeeById(Long id);

    ApiResponse getAllEmployees();

    ApiResponse getAllEmployeesSorted(String[] sort);

    ApiResponse getEmployeesPage(Integer page, Integer size, String[] sort);

    ApiResponse getAllEmployeesByDepartmentId(Long departmentId);

    ApiResponse getAllProjectionEmployees();

    ApiResponse getAllEmployeesByFilter(DataDto<EmployeeDto> dataDto);

    ApiResponse updateEmployee(Long id, DataDto<EmployeeDto> dataDto);

    ApiResponse deleteEmployee(Long id);

}
