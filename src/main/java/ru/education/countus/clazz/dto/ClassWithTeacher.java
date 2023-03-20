package ru.education.countus.clazz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.education.countus.teacher.model.Teacher;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassWithTeacher {

    private long id;

    private int studyYear;

    private String mnemocode;

    private Teacher teacher;
}