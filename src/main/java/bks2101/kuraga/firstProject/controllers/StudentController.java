package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.NotFoundByIdException;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.models.Student;
import bks2101.kuraga.firstProject.repository.StudentRepository;
import bks2101.kuraga.firstProject.service.StudentService;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping("/students")
    ResponseEntity getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    @PostMapping("/students")
    ResponseEntity newStudent(@RequestHeader("Authorization") String authHeader, @RequestBody StudentDto student) throws UserAlreadyExistsException {
        return studentService.createStudent(authHeader, student);
    }
    @PostMapping("/students/{username}/group")
    ResponseEntity setGroupStudent(@PathVariable String username) throws UserAlreadyExistsException {

        return ResponseEntity.ok("");
    }
}
