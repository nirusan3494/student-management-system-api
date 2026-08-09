package com.niraj.education.student.controller;

import com.niraj.education.student.dto.StudentRequestDto;
import com.niraj.education.student.dto.StudentResponseDto;
import com.niraj.education.student.entity.Student;
import com.niraj.education.student.repository.StudentRepository;
import com.niraj.education.student.service.StudentService;
import jakarta.validation.Valid;
import org.hibernate.internal.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<StudentResponseDto> addStudent(
            @RequestBody @Valid StudentRequestDto requestDTO){

        StudentResponseDto response =
                studentService.registerStudent(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
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


    @GetMapping
    public ResponseEntity<Page<StudentResponseDto>> getAllStudents(Pageable pageable){

        return ResponseEntity.ok(
                studentService.getAllStudents(pageable)
        );
    }


    @GetMapping("/search")
    public ResponseEntity<List<StudentResponseDto>> searchStudents(@RequestParam String name){
        return ResponseEntity.ok(
                studentService.getStudentsByName(name)
        );
    }


    @GetMapping("/search/details")
    public ResponseEntity<StudentResponseDto>getStudentByNameAndEmail(
            @RequestParam String name, @RequestParam String email
    ){
        return ResponseEntity.ok(
                studentService.getStudentByNameAndEmail(name, email)
        );
    }


    @GetMapping("/search/filter")
    public ResponseEntity<List<StudentResponseDto>>getStudentByNameOrEmail(
            @RequestParam String name, @RequestParam String email
    ){
        return ResponseEntity.ok(
                studentService.getStudentByNameOrEmail(name, email)
        );
    }

    @GetMapping("/search/start")
    public ResponseEntity<List<StudentResponseDto>>getStudentByPrefix(@RequestParam String prefix){

        return ResponseEntity.ok(
                studentService.getStudentsByPrefix(prefix)
        );
    }


    @GetMapping("/search/end")
    public ResponseEntity<List<StudentResponseDto>>getStudentBySuffix(@RequestParam String suffix){

        return ResponseEntity.ok(
                studentService.getStudentsBySuffix(suffix)
        );
    }

    @GetMapping("/search/id/greater")
    public ResponseEntity<List<StudentResponseDto>>getStudentByIdGreaterThan(@RequestParam Long id){
        return ResponseEntity.ok(
                studentService.getStudentsByIdGreaterThan(id)
        );
    }

    @GetMapping("/search/id/less")
    public ResponseEntity<List<StudentResponseDto>>getStudentByIdLessThan(@RequestParam Long id){
        return ResponseEntity.ok(
                studentService.getStudentsByIdLessThan(id)
        );
    }

    @GetMapping("/search/id/between")
    public ResponseEntity<List<StudentResponseDto>>getStudentByIdBetween(@RequestParam Long startId, @RequestParam Long endId){
        return ResponseEntity.ok(
                studentService.getStudentsByIdBetween(startId, endId));
    }

    @GetMapping("/search/order/asc")
    public ResponseEntity<List<StudentResponseDto>>findByNameOrderByIdAsc(@RequestParam String name){
        return ResponseEntity.ok(
                studentService.findByNameOrderByIdAsc(name));
    }

    @GetMapping("/search/order/desc")
    public ResponseEntity<List<StudentResponseDto>>findByNameOrderByIdDesc(@RequestParam String name){
        return ResponseEntity.ok(
                studentService.findByNameOrderByIdDesc(name));
    }

    @GetMapping("/search/top3")
    public ResponseEntity<List<StudentResponseDto>>findTop3ByOrderByIdDesc(){
        return ResponseEntity.ok(
                studentService.findTop3ByOrderByIdDesc());
    }


}
