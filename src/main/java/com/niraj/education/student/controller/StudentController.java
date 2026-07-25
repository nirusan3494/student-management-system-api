package com.niraj.education.student.controller;

import com.niraj.education.student.entity.Student;
import com.niraj.education.student.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody @Valid Student student){
        Student savedStudent=studentService.registerStudent(student);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
    }


}
