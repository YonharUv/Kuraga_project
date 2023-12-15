package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @GetMapping("/")
    ResponseEntity<Set<GroupDto>> getAllGroups() {
        return groupService.getAllGroups();
    }
    @PostMapping("/")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto group) throws UserAlreadyExistsException {
        return groupService.createGroup(group);
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteGroup(@PathVariable String name) throws UserNotFoundByUsernameException {
        return groupService.deleteGroupByName(name);
    }
    @PostMapping("/{name}")
    public ResponseEntity<GroupDto> updateGroupByName(@PathVariable String name, @RequestBody GroupDto group) throws UserNotFoundByUsernameException, UserAlreadyExistsException {
        return groupService.updateGroup(name, group);
    }
    @GetMapping("/{name}/students")
    public ResponseEntity<List<StudentDto>> getGroupStudentsByName(@PathVariable String groupName) throws UserNotFoundByUsernameException {
        return groupService.getGroupStudents(groupName);
    }
    @GetMapping("/{name}")
    public ResponseEntity<GroupDto> getGroupByUsername(@PathVariable String name) throws UserNotFoundByUsernameException {
        return groupService.getGroupByName(name);
    }
}
