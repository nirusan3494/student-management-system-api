package com.niraj.education.student.service;

import com.niraj.education.exception.EmailAlreadyExistsException;
import com.niraj.education.exception.StudentIDNotFoundException;
import com.niraj.education.exception.StudentNotFoundException;
import com.niraj.education.student.dto.StudentRequestDto;
import com.niraj.education.student.dto.StudentResponseDto;
import com.niraj.education.student.entity.Student;
import com.niraj.education.student.repository.StudentRepository;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }



    public StudentResponseDto registerStudent(StudentRequestDto requestDto) {

        Student student = new Student(
                requestDto.getName(),
                requestDto.getEmail()
        );
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "Student email already registered"
            );
        }
        Student savedStudent = studentRepository.save(student);
        return new StudentResponseDto(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail()
        );
    }



    public Page<StudentResponseDto> getAllStudents(Pageable pageable){

        Page<Student> students =
                studentRepository.findAll(pageable);

        return students.map(student ->
                new StudentResponseDto(
                        student.getId(),
                        student.getName(),
                        student.getEmail()
                )
        );
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


    public List<StudentResponseDto> getStudentsByName(String name){
        List<StudentResponseDto> response=new ArrayList<>();
        List<Student> students=studentRepository.findByNameContainingIgnoreCase(name);
        for(Student student:students){
            StudentResponseDto dto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail()
            );
            response.add(dto);
        }
        return response;
    }


    public StudentResponseDto getStudentByNameAndEmail(String name, String email){

        Student student = studentRepository
                .findByNameAndEmail(name, email)
                .orElseThrow(
                        () -> new StudentNotFoundException(
                                "Student not found"
                        )
                );

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail()
        );
    }


    public List<StudentResponseDto> getStudentByNameOrEmail(String name, String email){

        List<Student> students = studentRepository
                .findByNameOrEmail(name, email);
        List<StudentResponseDto> studentResponseDtos=new ArrayList<>();

        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }


    public List<StudentResponseDto> getStudentsByPrefix(String prefix){
        List<Student>students=studentRepository.findByNameStartingWith(prefix);

        List<StudentResponseDto>studentResponseDtos=new ArrayList<>();

        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }


}
