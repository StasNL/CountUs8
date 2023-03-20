package ru.education.countus.exceptions;

public enum ErrorType {
    STUDENT, TEACHER, CLASS;

    public static String useType(ErrorType type) {
        switch (type) {
            case STUDENT:
               return NotFoundException.errorStudentMessage;
            case TEACHER:
                return NotFoundException.errorTeacherMessage;
            case CLASS:
                return NotFoundException.errorClassMessage;
            default:
                throw new IllegalArgumentException("Данного типа сообщения не существует.");
        }
    }
}