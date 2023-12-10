package com.example.jpaspringdemo.services.impl;

import com.example.jpaspringdemo.dtos.ApiResponse;
import com.example.jpaspringdemo.dtos.DataDto;
import com.example.jpaspringdemo.dtos.EmployeeDto;
import com.example.jpaspringdemo.exceptions.IncorrectParameterException;
import com.example.jpaspringdemo.exceptions.NoSuchElementFoundException;
import com.example.jpaspringdemo.jpa.RecordState;
import com.example.jpaspringdemo.jpa.entities.Department;
import com.example.jpaspringdemo.jpa.entities.Employee;
import com.example.jpaspringdemo.jpa.specifications.EmployeeSpecification;
import com.example.jpaspringdemo.repositories.DepartmentRepository;
import com.example.jpaspringdemo.repositories.EmployeeRepository;
import com.example.jpaspringdemo.services.BaseService;
import com.example.jpaspringdemo.services.IEmployeeService;
import com.example.jpaspringdemo.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class EmployeeServiceImpl extends BaseService implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public ApiResponse addEmployee(Long departmentId, DataDto<EmployeeDto> dataDto) {
        checkData(dataDto);

        Optional<Department> op = departmentRepository.findByIdAndRecordState(departmentId, RecordState.ACTIVE);
        op.orElseThrow(() -> new NoSuchElementFoundException("no department found by id=" + departmentId));
        IncorrectParameterException e = new IncorrectParameterException();

        if (!StringUtils.hasText(dataDto.getData().getFullName())) {
            e.addParameter("fullName");
        }
        if (departmentId == null) {
            e.addParameter("departmentId");
        }
        if (dataDto.getData().getSalary() == null) {
            e.addParameter("salary");
        }
        if (!e.getParameters().isEmpty()) {
            throw e;
        }

        return ApiUtils.getApiResponse(employeeRepository.save(new Employee(dataDto.getData(), op.get())));
    }

    @Override
    public ApiResponse getEmployeeById(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findByIdAndRecordState(id, RecordState.ACTIVE);
        employeeOptional.orElseThrow(() -> new NoSuchElementFoundException("no employee found by id=" + id));
        return ApiUtils.getApiResponse(employeeOptional.get());
    }

    @Override
    public ApiResponse getAllEmployees() {
        return new ApiResponse("Employees", employeeRepository.findAllByRecordState(RecordState.ACTIVE));
    }

    public ApiResponse getAllEmployeesSorted(String[] sort) {
        List<Order> orders = extractOrders(sort);
        return new ApiResponse()
                .addData(
                        "Employees",
                        employeeRepository.findAllByRecordState(RecordState.ACTIVE, Sort.by(orders))
                );
    }

    @Override
    public ApiResponse getEmployeesPage(Integer page, Integer size, String[] sort) {
        List<Order> orders = extractOrders(sort);
        Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
        Page<Employee> empPage = employeeRepository.findAllByRecordState(RecordState.ACTIVE, pageable);

        return new ApiResponse()
                .addData("Employees", empPage.getContent())
                .addData("Current Page", page)
                .addData("Total Pages", empPage.getTotalPages());
    }

    @Override
    public ApiResponse getAllProjectionEmployees() {
        return new ApiResponse("Departments", employeeRepository.findAllProjectionDto());
    }

    @Override
    public ApiResponse getAllEmployeesByFilter(DataDto<EmployeeDto> dataDto) {
        if (dataDto == null || dataDto.getData() == null) {
            throw new IncorrectParameterException();
        }

        Specification<Employee> specification = Specification.where(EmployeeSpecification.idIsNotNull());
        if (dataDto.getData().getId() != null) {
            specification = specification.and(EmployeeSpecification.idEquals(dataDto.getData().getId()));
        }
        if (dataDto.getData().getFullName() != null) {
            specification = specification.and(EmployeeSpecification.likeFullName(dataDto.getData().getFullName()));
        }
        if (dataDto.getData().getSalary() != null) {
            specification = specification.and(EmployeeSpecification.hasSalaryEqual(dataDto.getData().getSalary()));
        }

        return new ApiResponse("Employees-Projection", employeeRepository.findAll(specification));
    }

    @Override
    public ApiResponse getAllEmployeesByDepartmentId(Long departmentId) {
        Optional<Department> op = departmentRepository.findByIdAndRecordState(departmentId, RecordState.ACTIVE);

        op.orElseThrow(() -> new NoSuchElementFoundException("no department found by id=" + departmentId));
        return new ApiResponse()
                .addData(
                        "Employees",
                        employeeRepository.findAllByDepartmentIdAndRecordState(departmentId, RecordState.ACTIVE)
                );
    }

    @Override
    public ApiResponse updateEmployee(Long id, DataDto<EmployeeDto> dataDto) {
        checkData(dataDto);
        Optional<Employee> employeeOptional = employeeRepository.findByIdAndRecordState(id, RecordState.ACTIVE);
        employeeOptional.orElseThrow(() -> new NoSuchElementFoundException("no employee found by id=" + id));

        Optional<Department> departmentOptional = departmentRepository
                .findByIdAndRecordState(dataDto.getData().getDepartmentId(), RecordState.ACTIVE);
        departmentOptional.orElseThrow(() ->
                new NoSuchElementFoundException("no department found by id=" + dataDto.getData().getDepartmentId()));

        Employee employee = employeeOptional.get();
        if (StringUtils.hasText(dataDto.getData().getFullName())) {
            employee.setFullName(dataDto.getData().getFullName());
        }
        if (dataDto.getData().getDepartmentId() != null) {
            employee.setDepartment(departmentOptional.get());
        }
        if (dataDto.getData().getSalary() != null) {
            employee.setSalary(dataDto.getData().getSalary());
        }

        return ApiUtils.getApiResponse(employeeRepository.save(employee));
    }

    @Override
    public ApiResponse deleteEmployee(Long id) {
        Optional<Employee> employeeOptional = employeeRepository.findByIdAndRecordState(id, RecordState.ACTIVE);

        employeeOptional.orElseThrow(() -> new NoSuchElementFoundException("no employee found by id=" + id));
        Employee employee = employeeOptional.get();
        employee.setRecordState(RecordState.DELETED);
        return ApiUtils.getApiResponse(employeeRepository.save(employee));
    }

}
