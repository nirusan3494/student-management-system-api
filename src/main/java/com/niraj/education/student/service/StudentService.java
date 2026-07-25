package com.niraj.education.student.service;

import com.niraj.education.exception.EmailAlreadyExistsException;
import com.niraj.education.student.entity.Student;
import com.niraj.education.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student registerStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Student email already registered"
            );
        }

        return studentRepository.save(student);
    }
}
