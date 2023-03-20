package ru.education.countus.clazz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassDto {

    private long id;

    private int studyYear;

    private String mnemocode;

    private String teacher;
}