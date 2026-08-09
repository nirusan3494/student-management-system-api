package com.niraj.education.department.controller;

import com.niraj.education.department.dto.DepartmentRequestDto;
import com.niraj.education.department.dto.DepartmentResponseDto;
import com.niraj.education.department.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {
private final DepartmentService departmentService;

@PostMapping
    public ResponseEntity<DepartmentResponseDto>createDepartment(@RequestBody DepartmentRequestDto requestDto) {
    DepartmentResponseDto departmentResponseDto=departmentService.createDepartment(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(departmentResponseDto);
}
}
