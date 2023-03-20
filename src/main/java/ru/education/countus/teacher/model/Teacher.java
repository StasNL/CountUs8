package ru.education.countus.teacher.model;

import lombok.*;
import ru.education.countus.clazz.model.Clazz;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "surname", nullable = false)
    private String surname;
    @Column(name = "patronymic", nullable = false)
    private String patronymic;
    @Column(name = "birth_year", nullable = false)
    private int birthYear;
    @Column(name = "gender", nullable = false)
    private String gender;
    @Column(name = "main_subject", nullable = false)
    private String mainSubject;

    @OneToOne()
    @JoinColumn(name = "class_id", referencedColumnName = "id")
    private Clazz clazz;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(id, teacher.id)
                && Objects.equals(name, teacher.name)
                && Objects.equals(surname, teacher.surname)
                && Objects.equals(patronymic, teacher.patronymic)
                && Objects.equals(birthYear, teacher.birthYear)
                && Objects.equals(gender, teacher.gender)
                && Objects.equals(mainSubject, teacher.mainSubject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, patronymic, birthYear, gender, mainSubject);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthYear +
                ", gender='" + gender + '\'' +
                ", mainSubject='" + mainSubject + '\'' +
                '}';
    }
}