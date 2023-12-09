package com.example.jpaspringdemo.jpa.entities;

import com.example.jpaspringdemo.dtos.EmployeeDto;
import com.example.jpaspringdemo.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Entity
@Table(name = "EMPLOYEES")
public class Employee extends BaseEntity {
  @Id
  @Column(name = "ID", updatable = false)
  @SequenceGenerator(name = "employeeIdSeq", sequenceName = "EMPLOYEE_ID_SEQ", initialValue = 0, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employeeIdSeq")
  private Long id;

  @Column(name = "FULL_NAME")
  private String fullName;

  @Column(name = "SALARY")
  private Integer salary;

  @ManyToOne
  private Department department;

  public Employee(EmployeeDto employeeDto, Department department) {
    if (employeeDto != null && department != null && employeeDto.getFullName() != null) {
      this.fullName = employeeDto.getFullName();
      this.salary = employeeDto.getSalary();
      this.department = department;
    }
  }
}
