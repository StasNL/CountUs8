package ru.education.countus.clazz.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.education.countus.clazz.dto.ClassDto;
import ru.education.countus.clazz.dto.ClassMapper;
import ru.education.countus.clazz.model.Clazz;
import ru.education.countus.clazz.repositories.ClassRepository;
import ru.education.countus.exceptions.NotFoundException;
import ru.education.countus.teacher.model.Teacher;
import ru.education.countus.teacher.service.TeacherService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.education.countus.exceptions.ErrorType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final TeacherService teacherService;

    @Override
    public ClassDto create(Clazz clazz) {
        clazz = classRepository.save(clazz);
        return ClassMapper.classToClassDto(clazz);
    }

    @Override
    public void delete(long id) {
        Long count = classRepository.countById(id);

        if (count == null || count == 0)
            throw new NotFoundException(useType(CLASS));

        classRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassDto> getAll(String sortBy, String sortDir, String filter) {
        Sort sort;
        if (sortDir.equals("asc"))
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();

        List<Clazz> clazzes;

        if (filter == null)
            clazzes = classRepository.findAll(sort);
        else {
            clazzes = classRepository.search(filter, sort);
        }
        return clazzes.stream()
                .map(ClassMapper::classToClassDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Clazz getById(long id) throws NotFoundException {
        return classRepository.findById(id).orElseThrow(() -> new NotFoundException(useType(CLASS)));
    }

    @Override
    public void addTeacher(Long teacherId, Long classId) {
        Clazz clazz = getById(classId);
        Teacher teacherFromClass = clazz.getTeacher();
        if (teacherFromClass != null)
            teacherFromClass.setClazz(null);
        Teacher teacherToAdd = teacherService.getById(teacherId);
        teacherToAdd.setClazz(clazz);
        teacherService.save(teacherToAdd);
    }

    @Override
    public void deleteTeacherFromClass(Long classId) throws NotFoundException {
        Clazz clazz = getById(classId);
        Teacher teacher;
        if (clazz.getTeacher() != null)
            teacher = clazz.getTeacher();
        else
            throw new NotFoundException(useType(TEACHER));
        teacher.setClazz(null);
        teacherService.save(teacher);
    }
}