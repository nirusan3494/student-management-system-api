package com.niraj.education.student.service;

import com.niraj.education.exception.EmailAlreadyExistsException;
import com.niraj.education.exception.StudentIDNotFoundException;
import com.niraj.education.student.entity.Student;
import com.niraj.education.student.repository.StudentRepository;
import org.hibernate.internal.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student registerStudent(Student student) {

        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Student email already registered"
            );
        }

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    public Student getStudentById(Long id){
        return studentRepository
                .findById(id)
                .orElseThrow(
                        ()->new StudentIDNotFoundException(
                                "Student not found with id:"+id
                        )
                );
    }

    public void deleteStudentById(Long id){
        studentRepository.findById(id)
                .orElseThrow(
                        () -> new StudentIDNotFoundException(
                                "Student not found with id: " + id
                        )
                );

        studentRepository.deleteById(id);
    }


    public Student updateStudentById(Long id, Student student){
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(
                        () -> new StudentIDNotFoundException(
                                "Student not found with id: " + id
                        )
                );
        existingStudent.setName(student.getName());
        existingStudent.setEmail(student.getEmail());
         return studentRepository.save(existingStudent);

    }

}
