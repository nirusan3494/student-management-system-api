package com.niraj.education.student.repository;

import com.niraj.education.student.dto.StudentResponseDto;
import com.niraj.education.student.entity.Student;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StudentRepository
        extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("""
            SELECT s
            FROM Student s
            WHERE LOWER(s.name) LIKE LOWER(CONCAT('%',:name, '%'))
            """)
    List<Student> searchByName(@Param("name")String name);

    @Query("""
            SELECT s
            FROM Student s
            WHERE LOWER(s.name)LIKE LOWER (CONCAT('%', :name, '%'))
             AND LOWER(s.email)LIKE LOWER(CONCAT('%',:email, '%'))
            """)
    Optional<Student> searchByNameAndEmail(@Param ("name")String name, @Param("email")String email);

    @Query("""
            SELECT s
            FROM Student s
            WHERE s.name=:name
               OR s.email=:email
            """)
    List<Student> searchByNameOrEmail(@Param("name")String name, @Param("email")String email);

    List<Student> findByNameStartingWith(String prefix);

    List<Student> findByNameEndingWith(String suffix);

    List<Student> findByIdGreaterThan(Long id);

    List<Student> findByIdLessThan(Long id);

   @Query("""
           SELECT s
           FROM Student s
           WHERE s.id BETWEEN :startId AND :endId
           """)
   List<Student> findStudentByIdBetween(@Param("startId")Long startId, @Param("endId")Long endId);

    @Query("""
    SELECT s
    FROM Student s
    ORDER BY s.id ASC
    """)
    List<Student> findStudentsOrderByIdAsc();

    @Query("""
    SELECT s
    FROM Student s
    ORDER BY s.id DESC
    """)
    List<Student> findStudentsOrderByIdDesc();

    List<Student> findTop3ByOrderByIdDesc();

    @Query("""
    SELECT COUNT(s)
    FROM Student s
    """)
    long countStudents();

    @Query("""
            SELECt Min(s.id)
            From Student s
            """)
    Long findMinimumStudentId();

    @Query("""
            SELECT MAX(s.id)
            FROM Student s
            """)
    Long findMaximumStudentId();


    @Query("""
    SELECT AVG(s.id)
    FROM Student s
    """)
    Double findAverageStudentId();

    @Query("""
            SELECT new com.niraj.education.student.dto.StudentResponseDto(
            s.id,
            s.name,
            s.email
            )
            FROM Student s
            """)
    List<StudentResponseDto>findStudentDetails();
}
