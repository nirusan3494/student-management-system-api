package com.niraj.education.student.repository;

import com.niraj.education.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    List<Student> findByName(String name);
}
