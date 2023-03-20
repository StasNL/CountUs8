package ru.education.countus.student.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.countus.exceptions.NotFoundException;
import ru.education.countus.student.dto.StudentDto;
import ru.education.countus.student.dto.StudentMapper;
import ru.education.countus.student.model.Student;
import ru.education.countus.student.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.education.countus.exceptions.ErrorType.STUDENT;
import static ru.education.countus.exceptions.ErrorType.useType;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    @Override
    public void save(Student student) {

        studentRepository.save(student);
    }

    @Transactional
    @Override
    public void delete(long id) throws NotFoundException {
        Long count = studentRepository.countById(id);

        if (count == null || count == 0)
            throw new NotFoundException(useType(STUDENT));

        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDto> getAll(String sortBy,
                                   String sortDir,
                                   String filter,
                                   Long classId) {
        Sort sort;
        if (sortDir.equals("asc"))
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();

        List<Student> students;

        if (filter == null && classId == null)
            students = studentRepository.findAll(sort);
        else if (classId == null) {
            students = studentRepository.search(filter, sort);
        } else if (filter == null)
            students = studentRepository.searchStudentByClazzId(classId, sort);
        else
            students = studentRepository.searchStudentsByClassIdWithFilter(classId, filter, sort);

        return students.stream()
                .map(StudentMapper::studentToDto)
                .collect(Collectors.toList());
    }

    public Student getById(long id) throws NotFoundException {
        return studentRepository.findById(id).orElseThrow(() -> new NotFoundException(useType(STUDENT)));
    }

    @Transactional
    @Override
    public void deleteFromClass(Long studentId) throws NotFoundException {
        Student student = getById(studentId);
        student.setClazz(null);
        studentRepository.save(student);
    }
}