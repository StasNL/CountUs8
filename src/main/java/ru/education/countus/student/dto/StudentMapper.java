package ru.education.countus.student.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.education.countus.student.model.Student;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StudentMapper {

    public static Student requestToStudent(StudentRequest request) {

        String gender;

        if (request.getMale())
            gender = "Мужской";
        else
            gender = "Женский";

        return Student.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .patronymic(request.getPatronymic())
                .birthYear(request.getBirthYear())
                .gender(gender)
                .build();
    }

    public static StudentRequest studentToRequest(Student student) {

        boolean male = student.getGender().equalsIgnoreCase("Мужской");

        return StudentRequest.builder()
                .name(student.getName())
                .surname(student.getSurname())
                .patronymic(student.getPatronymic())
                .birthYear(student.getBirthYear())
                .male(male)
                .build();
    }

    public static StudentDto studentToDto(Student student) {

        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .surname(student.getSurname())
                .patronymic(student.getPatronymic())
                .birthYear(student.getBirthYear())
                .gender(student.getGender())
                .build();
    }
}