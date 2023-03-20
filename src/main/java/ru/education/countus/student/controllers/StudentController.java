package ru.education.countus.student.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.education.countus.clazz.services.ClassService;
import ru.education.countus.exceptions.NotFoundException;
import ru.education.countus.student.dto.StudentDto;
import ru.education.countus.student.dto.StudentMapper;
import ru.education.countus.student.dto.StudentRequest;
import ru.education.countus.student.model.Student;
import ru.education.countus.student.service.StudentService;

import java.util.List;

import static ru.education.countus.utils.SortDir.ASC;
import static ru.education.countus.utils.SortDir.DESC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final ClassService classService;

    @GetMapping
    public String showStudents(Model model,
                               @RequestParam(value = "sortBy", required = false, defaultValue = "surname")
                               String sortBy,

                               @RequestParam(value = "sortDir", required = false, defaultValue = "asc")
                               String sortDir,

                               @RequestParam(value = "prevSort", required = false)
                               String prevSort,

                               @RequestParam(value = "filter", required = false)
                               String filter,

                               @RequestParam(value = "classId", required = false)
                               Long classId,

                               @RequestParam(value = "classIdToAdd", required = false)
                               Long classIdToAdd) {

        if (prevSort == null || !prevSort.equals(sortBy))
            sortDir = ASC.dir();

        List<StudentDto> students = studentService.getAll(
                sortBy,
                sortDir,
                filter,
                classId);
        prevSort = sortBy;

        String reverseSortDir;
        if (sortDir.equals(ASC.dir()))
            reverseSortDir = DESC.dir();
        else
            reverseSortDir = ASC.dir();

        String mnemocode = null;
        if (classId != null)
            mnemocode = classService.getById(classId).getMnemocode();

        model.addAttribute("students", students);
        model.addAttribute("prevSort", prevSort);
        model.addAttribute("filter", filter);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("classIdToAdd", classIdToAdd);
        model.addAttribute("classId", classId);
        model.addAttribute("mnemocode", mnemocode);

        return "students";
    }

    @GetMapping("/new")
    public String showNewStudent(Model model) {
        model.addAttribute("student", new StudentRequest());
        model.addAttribute("pageTitle", "Добавление ученика.");
        return "student_form";
    }

    @GetMapping("/duplicate/{id}")
    public String showDuplicateStudent(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
        try {
            Student student = studentService.getById(id);
            StudentRequest request = StudentMapper.studentToRequest(student);
            model.addAttribute("student", request);
            model.addAttribute("pageTitle", "Размножение");
            ra.addFlashAttribute("message", "Размножение прошло успешно");
            return "student_form";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/students";
        }
    }

    @PostMapping("/add")
    public String create(StudentRequest request, RedirectAttributes ra) {
        Student student = StudentMapper.requestToStudent(request);
        studentService.save(student);
        ra.addFlashAttribute("message", "Ученик успешно добавлен.");
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, RedirectAttributes ra) {

        try {
            studentService.delete(id);
            ra.addFlashAttribute("message", "Удаление прошло успешно.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/students";
    }

    @GetMapping("/deleteFromClass/{studentId}")
    public String deleteFromClass(RedirectAttributes ra,
                                  @PathVariable(value = "studentId") Long studentId,
                                  @RequestParam(value = "classId") Long classId) {
        try {
            studentService.deleteFromClass(studentId);
            ra.addFlashAttribute("message", "Удаление прошло успешно.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/students?classId=" + classId;
    }
}