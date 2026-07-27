package com.niraj.education.student.controller;

import com.niraj.education.student.entity.Student;
import com.niraj.education.student.service.StudentService;
import jakarta.validation.Valid;
import org.hibernate.internal.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents(){
        List <Student> students=studentService.getAllStudents();
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity <Student> getStudentById(@PathVariable Long id){
        Student student=studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id){
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudentById(@PathVariable Long id, @RequestBody @Valid Student student){
        Student updatedStudent=studentService.updateStudentById(id, student);
        return ResponseEntity.ok(updatedStudent);
    }

}
