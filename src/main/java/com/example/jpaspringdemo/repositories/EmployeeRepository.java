package com.example.jpaspringdemo.repositories;

import com.example.jpaspringdemo.jpa.entities.Employee;
import com.example.jpaspringdemo.jpa.entities.RecordState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByIdAndRecordState(Long id, RecordState recordState);

    List<Employee> findAllByRecordState(RecordState recordState);

    Optional<Employee> findByIdAndDepartmentIdAndRecordState(Long id, Long departmentId, RecordState recordState);

    List<Employee> findAllByDepartmentIdAndRecordState(Long departmentId, RecordState recordState);

}
