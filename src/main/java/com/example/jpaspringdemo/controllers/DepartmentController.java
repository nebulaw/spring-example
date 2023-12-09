package com.example.jpaspringdemo.controllers;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.DepartmentDto;
import com.example.jpaspringdemo.services.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/department")
@RestController
public class DepartmentController{

  private final IDepartmentService departmentService;

  @Autowired
  public DepartmentController(IDepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @PostMapping("/add")
  public ApiResponse addDepartment(@RequestBody DataDto<DepartmentDto> dataDTO) {
    return departmentService.addDepartment(dataDTO);
  }

  @GetMapping("/{id}")
  public ApiResponse getDepartment(@PathVariable Long id) {
    return departmentService.getDepartment(id);
  }

  @GetMapping("/page-{page}")
  public ApiResponse getDepartmentsOnPage(@PathVariable Integer page) {
    return page == null ? departmentService.getAllDepartments() : departmentService.getAllDepartments(page);
  }

  @GetMapping("/projection")
  public ApiResponse getAllProjectionDepartments() {
    return departmentService.getAllProjectionDepartments();
  }

  @PostMapping("/filter")
  public ApiResponse getAllDepartmentsByFilter(@RequestBody DataDto<DepartmentDto> dataDto) {
    return departmentService.getAllDepartmentsByFilter(dataDto);
  }

  @GetMapping
  public ApiResponse getAllDepartments() {
    return departmentService.getAllDepartments();
  }

  @PutMapping("/{id}")
  public ApiResponse updateDepartment(@PathVariable Long id, @RequestBody DataDto<DepartmentDto> dataDto) {
    return departmentService.updateDepartment(id, dataDto);
  }

  @DeleteMapping("/{id}")
  public ApiResponse deleteDepartment(@PathVariable Long id) {
    return departmentService.deleteDepartment(id);
  }

}
