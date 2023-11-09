package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.models.Student;
import bks2101.kuraga.firstProject.repository.StudentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
    private final StudentRepository studentRepository;
    public StudentController(StudentRepository repository) {
        this.studentRepository = repository;
    }
    @GetMapping("/students")
    List<Student> all() {
        return studentRepository.findAll();
    }
    @PostMapping("/students")
    Student newStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}
