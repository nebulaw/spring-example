package com.example.jpaspringdemo.jpa.entities;

import com.example.jpaspringdemo.dtos.DepartmentDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@Entity
@Table(name = "DEPARTMENTS")
public class Department extends BaseEntity {
  @Id
  @Column(name = "ID", updatable = false)
  @SequenceGenerator(name = "departmentIdSeq", sequenceName = "DEPARTMENT_ID_SEQ", initialValue = 0, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentIdSeq")
  private Long id;

  @Column(name = "NAME", nullable = false, insertable = true, updatable = true)
  private String name;

  public Department(DepartmentDto departmentDto) {
    if (departmentDto != null && departmentDto.getName() != null) {
      this.name = departmentDto.getName();
    }
  }

}
