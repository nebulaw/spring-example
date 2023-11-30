package com.example.jpaspringdemo.services;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.DepartmentDto;


public interface IDepartmentService {

  ApiResponse addDepartment(DataDto<DepartmentDto> dataDto);

  ApiResponse getDepartment(Long id);

  ApiResponse getAllDepartments();

  ApiResponse updateDepartment(Long id, DataDto<DepartmentDto> department);

  ApiResponse deleteDepartment(Long id);

}