package com.niraj.education.department.service;

import com.niraj.education.department.dto.DepartmentRequestDto;
import com.niraj.education.department.dto.DepartmentResponseDto;
import com.niraj.education.department.entity.Department;
import com.niraj.education.department.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
     Department department = new Department();
     department.setName(requestDto.getName());
     Department savedDepartment = departmentRepository.save(department);
     DepartmentResponseDto responseDto = new DepartmentResponseDto();
     responseDto.setId(department.getId());
     responseDto.setName(department.getName());
     return responseDto;
    }
}
