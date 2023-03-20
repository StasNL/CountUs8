package ru.education.countus.clazz.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.education.countus.clazz.model.Clazz;

import java.util.List;

public interface ClassRepository extends JpaRepository<Clazz, Long> {

    Long countById(Long id);

    @Query("SELECT c FROM Clazz c " +
            "WHERE CONCAT(UPPER(c.mnemocode), c.studyYear, UPPER(c.teacher.surname), " +
            "UPPER(c.teacher.name), UPPER(c.teacher.patronymic)) " +
            "LIKE CONCAT('%', UPPER(:filter), '%') ")
    List<Clazz> search(String filter, Sort sort);
}