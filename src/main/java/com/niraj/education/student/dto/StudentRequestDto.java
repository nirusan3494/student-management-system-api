package com.niraj.education.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


    @Getter
    @Setter
    public class StudentRequestDto {

        @NotBlank
        private String name;

        @NotBlank
        @Email
        private String email;

        private Long departmentId;

        public StudentRequestDto() {
        }
    }