package com.example.jpaspringdemo.repositories;

import com.example.jpaspringdemo.jpa.entities.Department;
import com.example.jpaspringdemo.jpa.entities.RecordState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

  Optional<Department> findByNameAndRecordState(String name, RecordState recordState);

  List<Department> findAllByRecordState(RecordState recordState);

  Optional<Department> findByIdAndRecordState(Long id, RecordState recordState);

//  @Query("select count(*) from departments d where d.record_state = 0")
//  Long getCount();

}
