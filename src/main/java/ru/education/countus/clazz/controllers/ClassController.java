package ru.education.countus.clazz.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.education.countus.clazz.dto.ClassDto;
import ru.education.countus.clazz.dto.ClassMapper;
import ru.education.countus.clazz.dto.ClassRequest;
import ru.education.countus.clazz.model.Clazz;
import ru.education.countus.clazz.services.ClassService;
import ru.education.countus.exceptions.NotFoundException;
import ru.education.countus.student.model.Student;
import ru.education.countus.student.service.StudentService;

import java.util.List;

import static ru.education.countus.utils.SortDir.ASC;
import static ru.education.countus.utils.SortDir.DESC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/classes")
public class ClassController {
    private final ClassService classService;
    private final StudentService studentService;

    @GetMapping
    public String showStudents(Model model,
                               @RequestParam(value = "sortBy", required = false, defaultValue = "mnemocode")
                               String sortBy,

                               @RequestParam(value = "sortDir", required = false, defaultValue = "asc")
                               String sortDir,

                               @RequestParam(value = "prevSort", required = false)
                               String prevSort,

                               @RequestParam(value = "filter", required = false)
                               String filter,

                               @RequestParam(value = "studentIdToAdd", required = false)
                               String studentIdToAdd,

                               @RequestParam(value = "teacherIdToAdd", required = false)
                               String teacherIdToAdd) {

        if (prevSort == null || !prevSort.equals(sortBy))
            sortDir = ASC.dir();

        List<ClassDto> classes = classService.getAll(
                sortBy,
                sortDir,
                filter);
        prevSort = sortBy;

        String reverseSortDir;
        if (sortDir.equals(ASC.dir()))
            reverseSortDir = DESC.dir();
        else
            reverseSortDir = ASC.dir();

        model.addAttribute("classes", classes);
        model.addAttribute("prevSort", prevSort);
        model.addAttribute("filter", filter);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("studentIdToAdd", studentIdToAdd);
        model.addAttribute("teacherIdToAdd", teacherIdToAdd);

        return "classes";
    }

    @GetMapping("/new")
    public String showNewClass(Model model) {
        model.addAttribute("class", new ClassRequest());
        model.addAttribute("pageTitle", "Добавление класса.");
        return "class_form";
    }

    @GetMapping("/duplicate/{id}")
    public String showDuplicateClass(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
        try {
            Clazz clazz = classService.getById(id);
            ClassRequest request = ClassMapper.classToRequest(clazz);
            model.addAttribute("class", request);
            model.addAttribute("pageTitle", "Размножение");
            ra.addFlashAttribute("message", "Размножение прошло успешно");
            return "class_form";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/classes";
        }
    }

    @PostMapping("/add")
    public String create(ClassRequest request, RedirectAttributes ra) {
        Clazz clazz = ClassMapper.requestToClass(request);
        classService.create(clazz);
        ra.addFlashAttribute("message", "Класс успешно добавлен.");
        return "redirect:/classes";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, RedirectAttributes ra) {
        try {
            classService.delete(id);
            ra.addFlashAttribute("message", "Удаление прошло успешно.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/classes";
    }

    @GetMapping("/add_student/{studentId}")
    public String addStudent(@PathVariable(value = "studentId") Long studentId,
                             @RequestParam(value = "classIdToAdd") Long classId) {
        Clazz clazz = classService.getById(classId);
        Student student = studentService.getById(studentId);
        student.setClazz(clazz);
        studentService.save(student);

        return "redirect:/students?classId=" + classId;
    }

    @GetMapping("/add_teacher/{teacherId}")
    public String addTeacher(@PathVariable(value = "teacherId") Long teacherId,
                             @RequestParam(value = "classIdToAdd") Long classId) {
        classService.addTeacher(teacherId, classId);

        return "redirect:/classes";
    }

    @GetMapping("/deleteTeacher/{classId}")
    public String deleteTeacherFromClass(RedirectAttributes ra, @PathVariable(value = "classId") Long classId) {
        try {
            classService.deleteTeacherFromClass(classId);
            ra.addFlashAttribute("message", "Удаление прошло успешно.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }

        return "redirect:/classes";
    }

    @GetMapping("/teacher")
    public String showTeacherInfo(Model model, @RequestParam(value = "classId") Long classId) {
        Clazz clazz = classService.getById(classId);
        model.addAttribute("clazz", clazz);

        return "teacher_info";
    }
}