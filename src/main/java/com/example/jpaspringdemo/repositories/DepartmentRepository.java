package com.example.jpaspringdemo.repositories;

import com.example.jpaspringdemo.dtos.projection.DepartmentProjectionDto;
import com.example.jpaspringdemo.jpa.RecordState;
import com.example.jpaspringdemo.jpa.entities.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long>,
        JpaSpecificationExecutor<Department>,
        PagingAndSortingRepository<Department, Long> {

    Optional<Department> findByNameAndRecordState(String name, RecordState recordState);

    List<Department> findAllByRecordState(RecordState recordState);

    Optional<Department> findByIdAndRecordState(Long id, RecordState recordState);

    @Query("select new com.example.jpaspringdemo.dtos.projection.DepartmentProjectionDto(d.name) from Department d")
    List<DepartmentProjectionDto> findAllProjectionDto();

    Page<Department> findAllByRecordState(RecordState recordState, Pageable pageable);

}
