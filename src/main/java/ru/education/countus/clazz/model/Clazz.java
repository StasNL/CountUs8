package ru.education.countus.clazz.model;

import lombok.*;
import ru.education.countus.teacher.model.Teacher;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "classes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clazz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "study_year")
    private int studyYear;

    @Column(name = "mnemocode", unique = true)
    private String mnemocode;

    @OneToOne(mappedBy = "clazz")
    private Teacher teacher;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clazz clazz = (Clazz) o;
        return id == clazz.id && studyYear == clazz.studyYear
                && Objects.equals(mnemocode, clazz.mnemocode)
                && Objects.equals(teacher, clazz.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studyYear, mnemocode, teacher);
    }

    @Override
    public String toString() {
        return "Clazz{" +
                "id=" + id +
                ", studyYear=" + studyYear +
                ", mnemocode='" + mnemocode + '\'' +
                ", teacher=" + teacher +
                '}';
    }
}