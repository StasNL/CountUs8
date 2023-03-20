package ru.education.countus.student.service;

import ru.education.countus.student.dto.StudentDto;
import ru.education.countus.student.model.Student;

import java.util.List;

public interface StudentService {
    void save(Student student);

    void delete(long id);

    List<StudentDto> getAll(String sortBy,
                            String sortDir,
                            String filter,
                            Long classId);
    Student getById(long id);

    void deleteFromClass(Long studentId);
}