package com.example.jpaspringdemo.controllers;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.EmployeeDto;
import com.example.jpaspringdemo.services.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class EmployeeController {

  private final IEmployeeService employeeService;

  @Autowired
  public EmployeeController(IEmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping("/department/{departmentId}/add")
  public ApiResponse addEmployee(@PathVariable Long departmentId, @RequestBody DataDto<EmployeeDto> dataDto) {
    return employeeService.addEmployee(departmentId, dataDto);
  }

  @GetMapping("/employee/{id}")
  public ApiResponse getEmployee(@PathVariable Long id) {
    return employeeService.getEmployee(id);
  }

  @GetMapping("/employee")
  public ApiResponse getAllEmployees() {
    return employeeService.getAllEmployees();
  }

  @GetMapping("/employee/projection")
  public ApiResponse getProjectionEmployees() {
    return employeeService.getAllProjectionEmployees();
  }

  @PostMapping("/filter")
  public ApiResponse getAllEmployeesByFilter(@RequestBody DataDto<EmployeeDto> dataDto) {
    return employeeService.getAllEmployeesByFilter(dataDto);
  }

  @GetMapping("/department/{departmentId}/employees")
  public ApiResponse getAllEmployeesInDepartment(@PathVariable Long departmentId) {
    return employeeService.getAllEmployeesByDepartmentId(departmentId);
  }

  @PutMapping("/employee/{id}")
  public ApiResponse updateEmployee(@PathVariable Long id, @RequestBody DataDto<EmployeeDto> dataDto) {
    return employeeService.updateEmployee(id, dataDto);
  }

  @DeleteMapping("/employee/{id}")
  public ApiResponse deleteEmployee(@PathVariable Long id) {
    return employeeService.deleteEmployee(id);
  }

}
