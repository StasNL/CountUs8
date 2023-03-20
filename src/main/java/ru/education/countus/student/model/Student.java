package ru.education.countus.student.model;

import lombok.*;
import ru.education.countus.clazz.model.Clazz;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "birth_year")
    private Integer birthYear;
    @Column(name = "gender")
    private String gender;

    @ManyToOne()
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Clazz clazz;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id)
                && Objects.equals(name, student.name)
                && Objects.equals(surname, student.surname)
                && Objects.equals(patronymic, student.patronymic)
                && Objects.equals(birthYear, student.birthYear)
                && Objects.equals(gender, student.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, birthYear, gender);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthYear +
                ", gender='" + gender + '\'' +
                '}';
    }
}