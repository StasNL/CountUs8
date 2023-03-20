package ru.education.countus.clazz.dto;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.education.countus.clazz.model.Clazz;
import ru.education.countus.teacher.model.Teacher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClassMapper {

    public static Clazz requestToClass(ClassRequest request) {
        return Clazz.builder()
                .mnemocode(request.getMnemocode())
                .studyYear(request.getStudyYear())
                .build();
    }

    public static ClassRequest classToRequest(Clazz clazz) {
        return ClassRequest.builder()
                .studyYear(clazz.getStudyYear())
                .mnemocode(clazz.getMnemocode())
                .build();
    }

    public static ClassDto classToClassDto(Clazz clazz) {
        Teacher teacher = clazz.getTeacher();
        String fullName = "Не назначен";
        if (clazz.getTeacher() != null)
            fullName = "" + teacher.getSurname() + " " + teacher.getName() + " " + teacher.getPatronymic();

        return ClassDto.builder()
                .id(clazz.getId())
                .studyYear(clazz.getStudyYear())
                .mnemocode(clazz.getMnemocode())
                .teacher(fullName)
                .build();
    }
}