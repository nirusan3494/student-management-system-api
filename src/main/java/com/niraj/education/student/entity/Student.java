package com.niraj.education.student.entity;
import com.niraj.education.department.entity.Department;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Student {

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    @NotBlank
    private String name;

    @Setter
    @Getter
    @NotBlank
    @Email
    private String email;

    @Setter
    @Getter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;

    protected Student(){}

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
