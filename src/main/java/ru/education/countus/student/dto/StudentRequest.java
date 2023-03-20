package ru.education.countus.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentRequest {

    private String name;

    private String surname;

    private String patronymic;

    private Integer birthYear;

    private Boolean male;
}