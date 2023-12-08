package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.lang.String.format;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @GetMapping("/groups")
    ResponseEntity<Set<GroupDto>> getAllGroups() {
        return groupService.getAllGroups();
    }
    @PostMapping("/groups")
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto group) throws UserAlreadyExistsException {
        return groupService.createGroup(group);
    }
    @GetMapping("/groups/{name}")
    public ResponseEntity<GroupDto> getGroupByUsername(@PathVariable String name) throws UserNotFoundByUsernameException {
        return groupService.getGroupByName(name);
    }
    @DeleteMapping("/groups/{name}")
    public ResponseEntity<String> deleteGroup(@PathVariable String name) throws UserNotFoundByUsernameException {
        return groupService.deleteGroupByName(name);
    }
    @PostMapping("/groups/{name}")
    public ResponseEntity<GroupDto> updateGroupByName(@PathVariable String name, @RequestBody GroupDto group) throws UserNotFoundByUsernameException, UserAlreadyExistsException {
        return groupService.updateGroup(name, group);
    }
    @GetMapping("groups/{name}/students")
    public ResponseEntity<Set<StudentDto>> getGroupStudentsByName(@PathVariable String groupName) throws UserNotFoundByUsernameException {
        return groupService.getGroupStudents(groupName);
    }
}
