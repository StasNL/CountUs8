package ru.education.countus.teacher.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.education.countus.teacher.model.Teacher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TeacherMapper {

    public static Teacher requestToTeacher(TeacherRequest request) {

        String gender;

        if (request.getMale())
            gender = "Мужской";
        else
            gender = "Женский";

        return Teacher.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .patronymic(request.getPatronymic())
                .birthYear(request.getBirthYear())
                .gender(gender)
                .mainSubject(request.getMainSubject())
                .build();
    }

    public static TeacherRequest teacherToRequest(Teacher teacher) {

        boolean male = teacher.getGender().equalsIgnoreCase("Мужской");

        return TeacherRequest.builder()
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .patronymic(teacher.getPatronymic())
                .birthYear(teacher.getBirthYear())
                .male(male)
                .mainSubject(teacher.getMainSubject())
                .build();
    }

    public static TeacherDto teacherToDto(Teacher teacher) {

        return TeacherDto.builder()
                .id(teacher.getId())
                .name(teacher.getName())
                .surname(teacher.getSurname())
                .patronymic(teacher.getPatronymic())
                .birthYear(teacher.getBirthYear())
                .gender(teacher.getGender())
                .mainSubject(teacher.getMainSubject())
                .build();
    }
}