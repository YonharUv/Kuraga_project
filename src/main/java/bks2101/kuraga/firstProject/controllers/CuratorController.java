package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.MeetingDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.GroupNotFoundByCurator;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.CuratorService;
import bks2101.kuraga.firstProject.service.GroupService;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/curator")
@RequiredArgsConstructor
public class CuratorController {
    private final CuratorService curatorService;
    public final JwtTokenUtils jwtTokenUtils;
    public final GroupService groupService;

    @GetMapping("/")
    ResponseEntity<CuratorDto> getCuratorInfo(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.getCuratorByEmail(curatorEmail);
    }
    @GetMapping("/groups")
    ResponseEntity<Set<GroupDto>> getCuratorGroups(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.getCuratorGroups(curatorEmail);
    }
    @GetMapping("/meetings")
    ResponseEntity<Set<MeetingDto>> getCuratorMeetings(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.getCuratorMeetings(curatorEmail);
    }
    @GetMapping("/{groupName}")
    ResponseEntity<GroupDto> getCuratorGroup(@RequestHeader("Authorization") String authHeader, @PathVariable String groupName) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.getCuratorGroupByName(curatorEmail, groupName);
    }
    @PostMapping("/{groupName}/meetings")
    ResponseEntity<MeetingDto> createGroupMeetings(@RequestHeader("Authorization") String authHeader, @PathVariable String groupName, @RequestBody MeetingDto meeting) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.createGroupMeetings(curatorEmail, groupName, meeting);
    }
    @GetMapping("/groups/{name}/students")
    public ResponseEntity<Set<StudentDto>> getGroupStudentsByName(@PathVariable String groupName) throws UserNotFoundByUsernameException {
        return groupService.getGroupStudents(groupName);
    }
    @GetMapping("/groups/{name}")
    public ResponseEntity<GroupDto> getGroupByUsername(@PathVariable String name) throws UserNotFoundByUsernameException {
        return groupService.getGroupByName(name);
    }
}
