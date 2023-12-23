package com.example.jpaspringdemo.controllers;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.DepartmentDto;
import com.example.jpaspringdemo.services.IDepartmentService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/department")
@RestController
public class DepartmentController {

    @Qualifier("departmentServiceImpl")
    private final IDepartmentService departmentService;

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

    @GetMapping("/projection")
    public ApiResponse getAllProjectionDepartments() {
        return departmentService.getAllProjectionDepartments();
    }

    @PostMapping("/filter")
    public ApiResponse getAllDepartmentsByFilter(@RequestBody DataDto<DepartmentDto> dataDto) {
        return departmentService.getAllDepartmentsByFilter(dataDto);
    }

    @GetMapping
    public ApiResponse getAllDepartmentsPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "3") Integer size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        return departmentService.getDepartmentsPage(page, size, sort);
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
