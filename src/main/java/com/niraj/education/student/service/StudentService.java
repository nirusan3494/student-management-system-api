package com.niraj.education.student.service;

import com.niraj.education.department.entity.Department;
import com.niraj.education.department.repository.DepartmentRepository;
import com.niraj.education.exception.DepartmentIDNotFoundException;
import com.niraj.education.exception.EmailAlreadyExistsException;
import com.niraj.education.exception.StudentIDNotFoundException;
import com.niraj.education.exception.StudentNotFoundException;
import com.niraj.education.student.dto.StudentRequestDto;
import com.niraj.education.student.dto.StudentResponseDto;
import com.niraj.education.student.entity.Student;
import com.niraj.education.student.repository.StudentRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private final DepartmentRepository departmentRepository;


    @PersistenceContext
    private EntityManager entityManager;
//    @Transactional
//    public void testDirtyChecking() {
//
//        Student student = studentRepository
//                .findById(2L)
//                .orElseThrow();
//
//        student.setName("Dirty Checking Test");
//    }


//

//    @Transactional
//    public void testDetached() {
//
//        Student student = studentRepository
//                .findById(15L)
//                .orElseThrow();
//
//        System.out.println(
//                "Before detach: " +
//                        entityManager.contains(student)
//        );
//
//        entityManager.detach(student);
//
//        System.out.println(
//                "After detach: " +
//                        entityManager.contains(student)
//        );
//
//        student.setName("Detached Test");
//    }

//    @Transactional
//    public void testMerge() {
//
//        Student student = studentRepository
//                .findById(15L)
//                .orElseThrow();
//
//        entityManager.detach(student);
//
//        System.out.println(
//                "Original managed? " +
//                        entityManager.contains(student)
//        );
//
//        student.setName("Merge Test");
//        //Hibernate takes the detached object's data and copies it into a managed entity
//        Student managedStudent =
//                entityManager.merge(student);
//
//        System.out.println(
//                "Original managed after merge? " +
//                        entityManager.contains(student)
//        );
//
//        System.out.println(
//                "Merged object managed? " +
//                        entityManager.contains(managedStudent)
//        );
//    }

//
//    @Transactional
//    public void testRemove() {
//
//        Student student = studentRepository
//                .findById(15L)
//                .orElseThrow();
//
//        System.out.println(
//                "Before remove: " +
//                        entityManager.contains(student)
//        );
//
//        entityManager.remove(student);
//
//        System.out.println(
//                "After remove: " +
//                        entityManager.contains(student)
//        );
//    }

//    @Transactional
//    public void testFlush() {
//
//        Student student = studentRepository
//                .findById(13L)
//                .orElseThrow();
//
//        student.setName("Flush Test");
//
//        System.out.println("Before flush");
//
//        entityManager.flush();
//
//        System.out.println("After flush");
//    }


//@Transactional
//public void testFlushRollback() {
//
//    Student student = studentRepository
//            .findById(14L)
//            .orElseThrow();
//
//    student.setName("Flush Rollback Test");
//
//    entityManager.flush();
//
//    System.out.println("UPDATE was flushed");
//
//    throw new RuntimeException("Testing rollback after flush");
//}
//@Transactional(readOnly = true)
//public Student testLazy() {
//
//    Student student = studentRepository
//            .findById(10L)
//            .orElseThrow();
//
//    System.out.println("Student loaded");
//
//    return student;
//}




    @Transactional
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

            Department department = departmentRepository
                    .findById(requestDto.getDepartmentId())
                    .orElseThrow(
                            () -> new DepartmentIDNotFoundException(
                                    "Department not found with id:" +
                                            requestDto.getDepartmentId()
                            )
                    );
        student.setDepartment(department);

        Student savedStudent = studentRepository.save(student);

        return new StudentResponseDto(
                savedStudent.getId(),
                savedStudent.getName(),
                savedStudent.getEmail(),
                savedStudent.getDepartment().getId()
        );
    }


    @Transactional(readOnly = true)
    public Page<StudentResponseDto> getAllStudents(Pageable pageable) {

        Page<Student> students =
                studentRepository.findAllWithDepartment(pageable);

        return students.map(student ->
                new StudentResponseDto(
                        student.getId(),
                        student.getName(),
                        student.getEmail(),
                        student.getDepartment().getId()
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
        List<Student> students=studentRepository.searchByName(name);
        for(Student student:students){
            StudentResponseDto dto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            response.add(dto);
        }
        return response;
    }


    public StudentResponseDto searchByNameAndEmail(String name, String email){

        Student student = studentRepository
                .searchByNameAndEmail(name, email)
                .orElseThrow(
                        () -> new StudentNotFoundException(
                                "Student not found"
                        )
                );

        return new StudentResponseDto(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getDepartment().getId()
        );
    }


    public List<StudentResponseDto> searchByNameOrEmail(String name, String email){

        List<Student> students = studentRepository
                .searchByNameOrEmail(name, email);
        List<StudentResponseDto> studentResponseDtos=new ArrayList<>();

        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
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
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }



    public List<StudentResponseDto> getStudentsBySuffix(String suffix){
        List<Student>students=studentRepository.findByNameEndingWith(suffix);

        List<StudentResponseDto>studentResponseDtos=new ArrayList<>();

        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }


    public List<StudentResponseDto>getStudentsByIdGreaterThan(Long id){
        List<Student> students=studentRepository.findByIdGreaterThan(id);
        List<StudentResponseDto>studentResponseDtos=new ArrayList<>();
        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }


    public List<StudentResponseDto>getStudentsByIdLessThan(Long id){
        List<Student> students=studentRepository.findByIdLessThan(id);
        List<StudentResponseDto>studentResponseDtos=new ArrayList<>();
        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }

    public List<StudentResponseDto>getStudentsByIdBetween(Long startId, Long endId){
        List<Student> students=studentRepository.findStudentByIdBetween(startId,endId);
        List<StudentResponseDto>studentResponseDtos=new ArrayList<>();
        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }

    public List<StudentResponseDto> getStudentsOrderByIdAsc() {

        List<Student> students =
                studentRepository.findStudentsOrderByIdAsc();

        List<StudentResponseDto> response = new ArrayList<>();

        for (Student student : students) {
            response.add(
                    new StudentResponseDto(
                            student.getId(),
                            student.getName(),
                            student.getEmail(),
                            student.getDepartment().getId()
                    )
            );
        }

        return response;
    }


    public List<StudentResponseDto> getStudentsOrderByIdDesc() {

        List<Student> students =
                studentRepository.findStudentsOrderByIdDesc();

        List<StudentResponseDto> response = new ArrayList<>();

        for (Student student : students) {
            response.add(
                    new StudentResponseDto(
                            student.getId(),
                            student.getName(),
                            student.getEmail(),
                            student.getDepartment().getId()
                    )
            );
        }

        return response;
    }




    public List<StudentResponseDto>findTop3ByOrderByIdDesc(){
        List<Student> students=studentRepository.findTop3ByOrderByIdDesc();
        List<StudentResponseDto>studentResponseDtos=new ArrayList<>();
        for(Student student:students){
            StudentResponseDto studentResponseDto = new StudentResponseDto(
                    student.getId(),
                    student.getName(),
                    student.getEmail(),
                    student.getDepartment().getId()
            );
            studentResponseDtos.add(studentResponseDto);
        }
        return studentResponseDtos;
    }


    public long getStudentCount() {
        return studentRepository.countStudents();
    }

    public Long getMinimumStudentId(){
        return studentRepository.findMinimumStudentId();
    }

    public Long getMaximumStudentId(){
        return studentRepository.findMaximumStudentId();
    }

    public Double getAverageStudentId() {
        return studentRepository.findAverageStudentId();
    }

    public List<StudentResponseDto> getStudentDetails(){
        return  studentRepository.findStudentDetails();
    }
}
