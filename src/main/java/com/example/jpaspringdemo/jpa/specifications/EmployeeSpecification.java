package com.example.jpaspringdemo.jpa.specifications;

import com.example.jpaspringdemo.jpa.entities.Department;
import com.example.jpaspringdemo.jpa.entities.Employee;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecification {

  private EmployeeSpecification() {}

  public static Specification<Employee> idEquals(Long id) {
    return (root, query, builder) -> builder.equal(root.get("ID"), id);
  }

  public static Specification<Employee> idIsNotNull() {
    return (root, query, builder) -> builder.isNotNull(root.get("ID"));
  }

  public static Specification<Employee> likeFullName(String fullName) {
    return (root, query, builder) -> builder.like(root.get("fullName"), "%" + fullName + "%");
  }

  public static Specification<Employee> inDepartment(Department department) {
    return inDepartment(department.getId());
  }

  public static Specification<Employee> inDepartment(Long id) {
    return (root, query, builder) -> builder.equal(root.get("departmentId"), id);
  }

  public static Specification<Employee> notInDepartment(Department department) {
    return notInDepartment(department.getId());
  }

  public static Specification<Employee> notInDepartment(Long id) {
    return (root, query, builder) -> builder.notEqual(root.get("ID"), id);
  }

  public static Specification<Employee> hasSalaryEqual(Integer salary) {
    return (root, query, builder) -> builder.equal(root.get("salary"), salary);
  }

  public static Specification<Employee> hasSalaryLessThan(Integer salary) {
    return (root, query, builder) -> builder.lessThan(root.get("salary"), salary);
  }

  public static Specification<Employee> hasSalaryGreaterThan(Integer salary) {
    return (root, query, builder) -> builder.greaterThan(root.get("salary"), salary);
  }

}
