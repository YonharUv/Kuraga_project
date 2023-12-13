package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.entitys.Group;
import bks2101.kuraga.firstProject.entitys.Student;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.repository.StudentRepository;
import bks2101.kuraga.firstProject.utils.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    public ResponseEntity<StudentDto> getStudentByEmail(String studentEmail) throws UserNotFoundByUsernameException {
        if (!studentRepository.existsByEmail(studentEmail)) {
            throw new UserNotFoundByUsernameException("Студент ", studentEmail);
        }
        Student student = studentRepository.findByEmail(studentEmail);
        StudentDto studentDto = MappingUtil.mapToStudentDto(student);
        return ResponseEntity.ok(studentDto);
    }

    public Student findStudentByEmail(String studentEmail) throws UserNotFoundByUsernameException {
        if (!studentRepository.existsByEmail(studentEmail)) {
            throw new UserNotFoundByUsernameException("Студент ", studentEmail);
        }
        Student student = studentRepository.findByEmail(studentEmail);
        return student;
    }
    public ResponseEntity<Set<StudentDto>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        Set<StudentDto> res = students.stream()
                .map(MappingUtil::mapToStudentDto)
                .collect(Collectors.toSet());
        return  ResponseEntity.ok(res);
    }
    public ResponseEntity<String> deleteStudentByEmail(String studentEmail) throws UserNotFoundByUsernameException {
        if (!studentRepository.existsByEmail(studentEmail)) {
            throw new UserNotFoundByUsernameException("Студент с эмейлом ", studentEmail);
        }
        Student student = studentRepository.findByEmail(studentEmail);
        studentRepository.delete(student);
        return ResponseEntity.ok("Студент(ка) с эмейлом " + studentEmail + " успешно удален(а)");
    }
}
