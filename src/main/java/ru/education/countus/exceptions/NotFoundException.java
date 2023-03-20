package ru.education.countus.exceptions;

public class NotFoundException extends RuntimeException{

    protected static final String errorStudentMessage = "Данный ученик не найден.";
    protected static final String errorTeacherMessage = "Данный учитель не найден.";
    protected static final String errorClassMessage = "Данный класс не найден.";

    public NotFoundException(String message) {
        super(message);
    }
}
