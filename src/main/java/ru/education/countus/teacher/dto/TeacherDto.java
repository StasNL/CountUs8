package ru.education.countus.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherDto {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private int birthYear;
    private String gender;
    private String mainSubject;
}
