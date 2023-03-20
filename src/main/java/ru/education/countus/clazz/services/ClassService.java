package ru.education.countus.clazz.services;

import ru.education.countus.clazz.dto.ClassDto;
import ru.education.countus.clazz.model.Clazz;

import java.util.List;

public interface ClassService {
    ClassDto create(Clazz request);

    void delete(long id);

    List<ClassDto> getAll(String sortBy,
                          String sortDir,
                          String filter);

    Clazz getById(long id);

    void addTeacher(Long teacherId, Long classId);

    void deleteTeacherFromClass(Long classId);
}