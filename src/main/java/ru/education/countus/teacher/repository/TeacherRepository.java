package ru.education.countus.teacher.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.education.countus.teacher.model.Teacher;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Long countById(Long id);

    @Query("SELECT t FROM Teacher t " +
            "WHERE CONCAT(UPPER(t.surname), UPPER(t.name), UPPER(t.patronymic), t.birthYear, UPPER(t.gender)," +
            " UPPER(t.mainSubject)) " +
            "LIKE CONCAT('%', UPPER(:filter), '%') ")
    List<Teacher> search(String filter, Sort sort);
}