package com.niraj.education.student.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);


}
