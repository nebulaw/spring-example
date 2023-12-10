package com.example.jpaspringdemo.services.impl;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.DepartmentDto;
import com.example.jpaspringdemo.exceptions.IncorrectParameterException;
import com.example.jpaspringdemo.exceptions.NoSuchElementFoundException;
import com.example.jpaspringdemo.jpa.RecordState;
import com.example.jpaspringdemo.jpa.entities.Department;
import com.example.jpaspringdemo.jpa.specifications.DepartmentSpecification;
import com.example.jpaspringdemo.repositories.DepartmentRepository;
import com.example.jpaspringdemo.services.BaseService;
import com.example.jpaspringdemo.services.IDepartmentService;
import com.example.jpaspringdemo.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;


@Service
public class DepartmentServiceImpl extends BaseService implements IDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ApiResponse addDepartment(DataDto<DepartmentDto> dataDto) {
        if (dataDto.getData() == null) {
            throw new IncorrectParameterException("Data");
        }
        if (!StringUtils.hasText(dataDto.getData().getName())) {
            throw new IncorrectParameterException("name");
        }
        return ApiUtils.getApiResponse(departmentRepository.save(new Department(dataDto.getData())));
    }

    @Override
    public ApiResponse getDepartment(Long id) {
        Optional<Department> departmentOptional = departmentRepository.findByIdAndRecordState(id, RecordState.ACTIVE);

        departmentOptional.orElseThrow(() -> new NoSuchElementFoundException("no department found by id=" + id));
        return ApiUtils.getApiResponse(departmentOptional.get());
    }

    @Override
    public ApiResponse getAllDepartments() {
        return new ApiResponse("Departments", departmentRepository.findAllByRecordState(RecordState.ACTIVE));
    }

    @Override
    public ApiResponse getAllDepartmentsSorted(String[] sort) {
        List<Order> orders = extractOrders(sort);
        return new ApiResponse("Departments", departmentRepository.findAll(Sort.by(orders)));
    }

    @Override
    public ApiResponse getDepartmentsPage(Integer page, Integer size, String[] sort) {
        List<Order> orders = extractOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<Department> depPage = departmentRepository.findAllByRecordState(RecordState.ACTIVE, pageable);

        return new ApiResponse()
                .addData("Departments", depPage.getContent())
                .addData("Current Page", page)
                .addData("Total Pages", depPage.getTotalPages());
    }

    @Override
    public ApiResponse getAllProjectionDepartments() {
        return new ApiResponse("Departments", departmentRepository.findAllProjectionDto());
    }

    @Override
    public ApiResponse getAllDepartmentsByFilter(DataDto<DepartmentDto> dataDto) {
        if (dataDto == null || dataDto.getData() == null) {
            throw new IncorrectParameterException();
        }

        Specification<Department> specification = Specification.where(DepartmentSpecification.idIsNotNull());
        if (dataDto.getData().getId() != null) {
            specification = specification.and(DepartmentSpecification.idEquals(dataDto.getData().getId()));
        }
        if (dataDto.getData().getName() != null) {
            specification = specification.and(DepartmentSpecification.likeName(dataDto.getData().getName()));
        }

        return new ApiResponse("Departments", departmentRepository.findAll(specification));
    }

    @Override
    public ApiResponse updateDepartment(Long id, DataDto<DepartmentDto> dataDto) {
        Optional<Department> departmentOptional = departmentRepository.findByIdAndRecordState(id, RecordState.ACTIVE);
        departmentOptional.orElseThrow(() -> new NoSuchElementFoundException("no department found by id=" + id));
        Department department1 = departmentOptional.get();
        department1.setName(dataDto.getData().getName());

        return ApiUtils.getApiResponse(departmentRepository.save(department1));
    }

    @Override
    public ApiResponse deleteDepartment(Long id) {
        Optional<Department> departmentOptional = departmentRepository.findByIdAndRecordState(id, RecordState.ACTIVE);
        departmentOptional.orElseThrow(() -> new NoSuchElementFoundException("no department found by id=" + id));
        Department department1 = departmentOptional.get();
        department1.setRecordState(RecordState.DELETED);

        return ApiUtils.getApiResponse(departmentRepository.save(department1));
    }

}
