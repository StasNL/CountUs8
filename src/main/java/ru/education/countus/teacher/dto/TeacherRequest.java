package ru.education.countus.teacher.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherRequest {
    private String name;
    private String surname;
    private String patronymic;
    private Integer birthYear;
    private Boolean male;
    private String mainSubject;
}