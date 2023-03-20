package ru.education.countus.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {

    private long id;

    private String name;

    private String surname;

    private String patronymic;

    private int birthYear;

    private String gender;
}