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
    private final StudentRepository repository;
    public StudentController(StudentRepository repository) {
        this.repository = repository;
    }
    @GetMapping("/students")
    List<Student> all() {
        return repository.findAll();
    }
    @PostMapping("/students")
    Student newStudent(@RequestBody Student student) {
        return repository.save(student);
    }
}
