package com.niraj.education.department.entity;

import com.niraj.education.student.entity.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Department(String name) {
        this.name = name;
    }

    @OneToMany(
            mappedBy = "department",
            orphanRemoval = true

    )
    private List<Student> students = new ArrayList<>();
}
