package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.entitys.Student;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.GroupService;
import bks2101.kuraga.firstProject.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @GetMapping("/")
    ResponseEntity<Set<StudentDto>> getAllGroups() {
        return studentService.getAllStudents();
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteGroup(@PathVariable String email) throws UserNotFoundByUsernameException {
        return studentService.deleteStudentByEmail(email);
    }
    @GetMapping("/{email}")
    public ResponseEntity<StudentDto> getGroupByUsername(@PathVariable String email) throws UserNotFoundByUsernameException {
        return studentService.getStudentByEmail(email);
    }
}
