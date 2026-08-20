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


//    @GetMapping("/test-dirty")
//    public void testDirtyChecking() {
//        studentService.testDirtyChecking();
//    }


//    @GetMapping("/test-detached")
//    public void testDetached() {
//        studentService.testDetached();
//    }


//    @GetMapping("/test-merge")
//    public void testMerge() {
//        studentService.testMerge();
//    }
//@GetMapping("/test-remove")
//public void testRemove() {
//    studentService.testRemove();
//}

//    @GetMapping("/test-flush")
//    public void testFlush() {
//        studentService.testFlush();
//    }

@GetMapping("/test-flush-rollback")
public void testFlushRollback() {
    studentService.testFlushRollback();
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


    @GetMapping("/search/jpql/and")
    public ResponseEntity<StudentResponseDto>getStudentByNameAndEmail(
            @RequestParam String name, @RequestParam String email
    ){
        return ResponseEntity.ok(
                studentService.searchByNameAndEmail(name, email)
        );
    }


    @GetMapping("/search/jpql/or")
    public ResponseEntity<List<StudentResponseDto>>getStudentByNameOrEmail(
            @RequestParam String name, @RequestParam String email
    ){
        return ResponseEntity.ok(
                studentService.searchByNameOrEmail(name, email)
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

    @GetMapping("/search/jpql/between")
    public ResponseEntity<List<StudentResponseDto>>getStudentByIdBetween(@RequestParam Long startId, @RequestParam Long endId){
        return ResponseEntity.ok(
                studentService.getStudentsByIdBetween(startId, endId));
    }

    @GetMapping("/search/jpql/order/asc")
    public ResponseEntity<List<StudentResponseDto>> getStudentsOrderByIdAsc() {
        return ResponseEntity.ok(
                studentService.getStudentsOrderByIdAsc()
        );
    }

    @GetMapping("/search/jpql/order/desc")
    public ResponseEntity<List<StudentResponseDto>> getStudentsOrderByIdDesc() {
        return ResponseEntity.ok(
                studentService.getStudentsOrderByIdDesc()
        );
    }

    @GetMapping("/search/top3")
    public ResponseEntity<List<StudentResponseDto>>findTop3ByOrderByIdDesc(){
        return ResponseEntity.ok(
                studentService.findTop3ByOrderByIdDesc());
    }

    @GetMapping("/search/jpql/count")
    public ResponseEntity<Long> getStudentCount() {
        return ResponseEntity.ok(
                studentService.getStudentCount()
        );
    }

    @GetMapping("/search/jpql/min-id")
    public ResponseEntity<Long> getMinimumStudentId() {
        return ResponseEntity.ok(
                studentService.getMinimumStudentId()
        );
    }


    @GetMapping("/search/jpql/max-id")
    public ResponseEntity<Long> getMaximumStudentId() {
        return ResponseEntity.ok(
                studentService.getMaximumStudentId()
        );
    }


    @GetMapping("/search/jpql/avg-id")
    public ResponseEntity<Double> getAverageStudentId() {
        return ResponseEntity.ok(
                studentService.getAverageStudentId()
        );
    }


    @GetMapping("/search/jpql/details")
    public ResponseEntity<List<StudentResponseDto>> getStudentDetails(){
        return ResponseEntity.ok(
                studentService.getStudentDetails()
        );
    }


}
