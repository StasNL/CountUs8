package ru.education.countus.teacher.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.countus.exceptions.NotFoundException;
import ru.education.countus.teacher.dto.TeacherDto;
import ru.education.countus.teacher.dto.TeacherMapper;
import ru.education.countus.teacher.model.Teacher;
import ru.education.countus.teacher.repository.TeacherRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.education.countus.exceptions.ErrorType.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository teacherRepository;

    @Transactional
    @Override
    public void save(Teacher teacher) {
        teacher = teacherRepository.save(teacher);
        TeacherMapper.teacherToDto(teacher);
    }

    @Transactional
    @Override
    public void delete(long id) {
        Long count = teacherRepository.countById(id);

        if (count == null || count == 0)
            throw new NotFoundException(useType(TEACHER));

        teacherRepository.deleteById(id);
    }

    @Override
    public List<TeacherDto> getAll(String sortBy, String sortDir, String filter) {
        Sort sort;
        if (sortDir.equals("asc"))
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();

        List<Teacher> teacher;

        if (filter == null)
            teacher = teacherRepository.findAll(sort);
        else {
            teacher = teacherRepository.search(filter, sort);
        }

        return teacher.stream()
                .map(TeacherMapper::teacherToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Teacher getById(long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new NotFoundException(useType(TEACHER)));
    }
}