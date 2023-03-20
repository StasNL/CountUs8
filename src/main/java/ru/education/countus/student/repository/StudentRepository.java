package ru.education.countus.student.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.education.countus.student.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Long countById(Long id);

    @Query("SELECT s FROM Student s " +
            "WHERE CONCAT(UPPER(s.surname), UPPER(s.name), UPPER(s.patronymic), s.birthYear, UPPER(s.gender)) " +
            "LIKE CONCAT('%', UPPER(:filter), '%')")
    List<Student> search(String filter, Sort sort);

    List<Student> searchStudentByClazzId(Long classId, Sort sort);

    @Query("SELECT s FROM Student s " +
            "WHERE s.clazz.id = :classId AND " +
            "CONCAT(UPPER(s.surname), UPPER(s.name), UPPER(s.patronymic), s.birthYear, UPPER(s.gender)) " +
            "LIKE CONCAT('%', UPPER(:filter), '%')")
    List<Student> searchStudentsByClassIdWithFilter(Long classId, String filter, Sort sort);
}