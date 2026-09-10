package com.niraj.education.department.repository;

import com.niraj.education.department.entity.Department;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    @EntityGraph(attributePaths = "students")
    Optional<Department> findWithStudentsById(Long id);


}
