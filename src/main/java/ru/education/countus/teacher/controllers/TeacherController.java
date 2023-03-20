package ru.education.countus.teacher.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.education.countus.exceptions.NotFoundException;
import ru.education.countus.teacher.dto.TeacherDto;
import ru.education.countus.teacher.dto.TeacherMapper;
import ru.education.countus.teacher.dto.TeacherRequest;
import ru.education.countus.teacher.model.Teacher;
import ru.education.countus.teacher.service.TeacherService;

import java.util.List;

import static ru.education.countus.utils.SortDir.ASC;
import static ru.education.countus.utils.SortDir.DESC;

@Controller
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public String showTeachers(Model model,
                               @RequestParam(value = "sortBy", required = false, defaultValue = "surname")
                               String sortBy,

                               @RequestParam(value = "sortDir", required = false, defaultValue = "asc")
                               String sortDir,

                               @RequestParam(value = "prevSort", required = false)
                               String prevSort,

                               @RequestParam(value = "filter", required = false)
                               String filter,

                               @RequestParam(value = "class_id", required = false)
                               Long classId,

                               @RequestParam(value = "classIdToAdd", required = false)
                               String classIdToAdd) {

        if (prevSort == null || !prevSort.equals(sortBy))
            sortDir = ASC.dir();

        List<TeacherDto> teachers = teacherService.getAll(
                sortBy,
                sortDir,
                filter);
        prevSort = sortBy;

        String reverseSortDir;
        if (sortDir.equals(ASC.dir()))
            reverseSortDir = DESC.dir();
        else
            reverseSortDir = ASC.dir();

        model.addAttribute("teachers", teachers);
        model.addAttribute("prevSort", prevSort);
        model.addAttribute("filter", filter);
        model.addAttribute("reverseSortDir", reverseSortDir);
        model.addAttribute("classIdToAdd", classIdToAdd);
        model.addAttribute("classId", classId);

        return "teachers";
    }

    @GetMapping("/new")
    public String showNewTeacher(Model model) {
        model.addAttribute("teacher", new TeacherRequest());
        model.addAttribute("pageTitle", "Добавление учителя.");
        return "teacher_form";
    }

    @GetMapping("/duplicate/{id}")
    public String showDuplicateTeacher(@PathVariable(name = "id") Long id, Model model, RedirectAttributes ra) {
        try {
            Teacher teacher = teacherService.getById(id);
            TeacherRequest request = TeacherMapper.teacherToRequest(teacher);
            model.addAttribute("teacher", request);
            model.addAttribute("pageTitle", "Размножение");
            ra.addFlashAttribute("message", "Размножение прошло успешно");
            return "teacher_form";
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/teachers";
        }
    }

    @PostMapping("/add")
    public String create(TeacherRequest request, RedirectAttributes ra) {
        Teacher teacher = TeacherMapper.requestToTeacher(request);
        teacherService.save(teacher);
        ra.addFlashAttribute("message", "Учитель успешно добавлен.");
        return "redirect:/teachers";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") Long id, RedirectAttributes ra) {
        try {
            teacherService.delete(id);
            ra.addFlashAttribute("message", "Удаление прошло успешно.");
        } catch (NotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/teachers";
    }
}