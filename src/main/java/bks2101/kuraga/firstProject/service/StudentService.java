package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.models.Group;
import bks2101.kuraga.firstProject.models.Student;
import bks2101.kuraga.firstProject.repository.StudentRepository;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final JwtTokenUtils jwtTokenUtils;
    private final StudentRepository studentRepository;
    private final GroupService groupService;

    public ResponseEntity createStudent(String authHeader, StudentDto studentDto) throws UserAlreadyExistsException {
        String jwtToken = authHeader.substring(7);
        String username = jwtTokenUtils.getUsername(jwtToken);
        if (studentRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException("Студент", username);
        }
        Student student = new Student();
        student.setFirst_name(studentDto.getFirst_name());
        student.setLast_name(studentDto.getLast_name());
        student.setPersonal_data(studentDto.getPersonal_data());
        student.setUsername(username);
        studentRepository.save(student);
        return ResponseEntity.ok("Студент успешно создан");
    }
    public ResponseEntity getAllStudents() {
        return ResponseEntity.ok(studentRepository.findAll());
    }

    public ResponseEntity setStudentGroup(String authHeader, GroupDto groupName) throws UserAlreadyExistsException {
        String jwtToken = authHeader.substring(7);
        String username = jwtTokenUtils.getUsername(jwtToken);
        Student student = studentRepository.findByUsername(username);
        Group group = groupService.getGroupByName(groupName.getName());
        student.setGroup(group);
        student.setCurator(null);
        return ResponseEntity.ok(studentRepository.save(student));
    }
}
