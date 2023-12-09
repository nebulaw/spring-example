package com.example.jpaspringdemo.jpa.specifications;


import com.example.jpaspringdemo.jpa.entities.Department;
import org.springframework.data.jpa.domain.Specification;

public final class DepartmentSpecification {

  private DepartmentSpecification() {}

  public static Specification<Department> idEquals(Long id) {
    return (root, query, builder) -> builder.equal(root.get("ID"), id);
  }

  public static Specification<Department> idIsNotNull() {
    return (root, query, builder) -> builder.isNotNull(root.get("ID"));
  }

  public static Specification<Department> likeName(String name) {
    return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
  }

}
