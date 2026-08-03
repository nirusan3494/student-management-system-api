package com.niraj.education.student.repository;

import com.niraj.education.student.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findByNameContainingIgnoreCase(String name);

    Optional<Student>findByNameAndEmail(String name,String email);

    List<Student>findByNameOrEmail(String name,String email);

    List<Student> findByNameStartingWith(String prefix);

    List<Student> findByNameEndingWith(String suffix);
}
