package com.example.jpaspringdemo.repositories;

import com.example.jpaspringdemo.dtos.projection.EmployeeProjectionDto;
import com.example.jpaspringdemo.jpa.entities.Employee;
import com.example.jpaspringdemo.jpa.RecordState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Optional<Employee> findByIdAndRecordState(Long id, RecordState recordState);

    List<Employee> findAllByRecordState(RecordState recordState);

    Optional<Employee> findByIdAndDepartmentIdAndRecordState(Long id, Long departmentId, RecordState recordState);

    List<Employee> findAllByDepartmentIdAndRecordState(Long departmentId, RecordState recordState);

    @Query("SELECT new com.example.jpaspringdemo.dtos.projection.EmployeeProjectionDto(e.fullName, e.salary) FROM Employee e")
    List<EmployeeProjectionDto> findAllProjectionDto();

    List<Employee> findAllByRecordState(RecordState recordState, Sort sort);

    Page<Employee> findAllByRecordState(RecordState recordState, Pageable pageable);

}
