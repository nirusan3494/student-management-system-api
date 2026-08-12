package com.niraj.education.department.controller;

import com.niraj.education.department.dto.DepartmentRequestDto;
import com.niraj.education.department.dto.DepartmentResponseDto;
import com.niraj.education.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
private final DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentResponseDto>createDepartment( @Valid @RequestBody DepartmentRequestDto requestDto) {
         DepartmentResponseDto departmentResponseDto=departmentService.createDepartment(requestDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(departmentResponseDto);
}

    @GetMapping

    public ResponseEntity<List<DepartmentResponseDto>>getAllDepartments() {
         List<DepartmentResponseDto> response=departmentService.getAllDepartments();
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        DepartmentResponseDto response=departmentService.getDepartmentsById(id);
        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto>updateDepartment(@PathVariable Long id,
    @RequestBody DepartmentRequestDto requestDto) {
        DepartmentResponseDto responseDto=departmentService.updateDepartment(id,requestDto);
        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DepartmentResponseDto>deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
