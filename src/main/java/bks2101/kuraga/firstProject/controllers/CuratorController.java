package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.*;
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
    ResponseEntity<List<GroupDto>> getCuratorGroups(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
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
//    @GetMapping("/{groupName}/meetings/{id}")
//    ResponseEntity<?> getMeeting(@RequestHeader("Authorization") String authHeader, @PathVariable String groupName, @PathVariable long id) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
//        String jwtToken = authHeader.substring(7);
//        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
//        return curatorService.getMeeting(curatorEmail, groupName, id);
//    }
//    @PostMapping("/{groupName}/meetings/{id}")
//    ResponseEntity<String> updateAttendanceList(@RequestHeader("Authorization") String authHeader, @PathVariable String groupName, @PathVariable long id, @RequestBody ListAttendance list) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
//        String jwtToken = authHeader.substring(7);
//        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
//        return curatorService.updateAttendanceList(curatorEmail, groupName, id, list);
//    }
    @GetMapping("/groups/{name}/students")
    public ResponseEntity<List<StudentDto>> getGroupStudentsByName(@PathVariable String groupName) throws UserNotFoundByUsernameException {
        return groupService.getGroupStudents(groupName);
    }
    @PostMapping("/{groupName}/meetings/{id}")
    public ResponseEntity<?> UpdateListAttendance(@RequestHeader("Authorization") String authHeader, @PathVariable String groupName, @PathVariable long id, @RequestBody MeetingList studentsList) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.updateList(curatorEmail, groupName, id, studentsList);
    }
    @GetMapping("/{groupName}/meetings/{id}")
    public ResponseEntity<?> UpdateListAttendance(@RequestHeader("Authorization") String authHeader, @PathVariable String groupName, @PathVariable long id) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        String jwtToken = authHeader.substring(7);
        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
        return curatorService.getList(curatorEmail, groupName, id);
    }
    @GetMapping("/groups/{name}")
    public ResponseEntity<GroupDto> getGroupByUsername(@PathVariable String name) throws UserNotFoundByUsernameException {
        return groupService.getGroupByName(name);
    }
}
