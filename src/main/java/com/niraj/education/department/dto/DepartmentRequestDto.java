package com.niraj.education.department.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @Getter
    @Setter
    @NoArgsConstructor
    public class DepartmentRequestDto {

       @NotBlank(message="Department name can't be null")
        private String name;

}
