package ru.education.countus.teacher.service;

import ru.education.countus.teacher.dto.TeacherDto;
import ru.education.countus.teacher.model.Teacher;

import java.util.List;

public interface TeacherService {
    void save(Teacher request);

    void delete(long id);

    List<TeacherDto> getAll(String sortBy,
                            String sortDir,
                            String filter);
    Teacher getById(long id);
}
