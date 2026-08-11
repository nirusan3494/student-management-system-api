package com.niraj.education.department.service;

import com.niraj.education.department.dto.DepartmentRequestDto;
import com.niraj.education.department.dto.DepartmentResponseDto;
import com.niraj.education.department.entity.Department;
import com.niraj.education.department.repository.DepartmentRepository;
import com.niraj.education.exception.DepartmentIDNotFoundException;
import com.niraj.education.exception.StudentIDNotFoundException;
import com.niraj.education.student.entity.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<DepartmentResponseDto> getAllDepartments(){
        List<Department>departments=departmentRepository.findAll();
        List<DepartmentResponseDto> response=new ArrayList<>();
        for(Department department:departments){
            DepartmentResponseDto dto=new DepartmentResponseDto();
            dto.setId(department.getId());
            dto.setName(department.getName());

            response.add(dto);
        }
        return response;
    }

    public DepartmentResponseDto getDepartmentsById(Long id) {
    Department department=departmentRepository
            .findById(id)
            .orElseThrow(
                    () -> new DepartmentIDNotFoundException(
                            "Department with id " + id + " not found"));
    DepartmentResponseDto dto=new DepartmentResponseDto();
    dto.setId(department.getId());
    dto.setName(department.getName());

    return dto;
    }

}
