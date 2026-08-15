package com.niraj.education.student.dto;

public class StudentResponseDto {

    private Long id;
    private String name;
    private String email;
    private Long departmentId;

    public StudentResponseDto(){}

    public StudentResponseDto(Long id, String name, String email,Long departmentId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
