package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.*;
import bks2101.kuraga.firstProject.exceptions.GroupNotFoundByCurator;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.CuratorService;
import bks2101.kuraga.firstProject.service.SupervisorService;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static java.lang.String.format;

@RestController
@RequestMapping("/supervisor")
@RequiredArgsConstructor
public class SupervisorController {

    private final SupervisorService supervisorService;
    private final CuratorService curatorService;
    public final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/")
    public ResponseEntity getSupervisorByEmail(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
        String jwtToken = authHeader.substring(7);
        String email = jwtTokenUtils.getUsername(jwtToken);
        return supervisorService.getSupervisorByEmail(email);
    }

    @GetMapping("/curators/{curatorEmail}/groups")
    ResponseEntity<Set<GroupDto>> getCuratorGroupsByName(@PathVariable String curatorEmail) throws UserNotFoundByUsernameException {
        return curatorService.getCuratorGroups(curatorEmail);
    }
    @GetMapping("/curators/{curatorEmail}")
    ResponseEntity<CuratorDto> getCuratorByName(@PathVariable String curatorEmail) throws UserNotFoundByUsernameException {
        return curatorService.getCuratorByEmail(curatorEmail);
    }
    @PostMapping("/curators")
    public ResponseEntity addCurators(@RequestHeader("Authorization") String authHeader, @RequestBody RequestAddCurators curators_emails) {
        String jwtToken = authHeader.substring(7);
        String email = jwtTokenUtils.getUsername(jwtToken);
        return supervisorService.addCurators(email, curators_emails);
    }
    @GetMapping("/curator/{curatorEmail}/groups/meetings")
    ResponseEntity<Set<MeetingDto>> getCuratorMeetingsByName(@PathVariable String curatorEmail) throws UserNotFoundByUsernameException {
        return curatorService.getCuratorMeetings(curatorEmail);
    }
    @GetMapping("/curator/{curatorEmail}/{groupName}/")
    ResponseEntity<GroupDto> getCuratorGroupByName(@PathVariable String curatorEmail, @PathVariable String groupName) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        return curatorService.getCuratorGroupByName(curatorEmail, groupName);
    }
    @PostMapping("/curator/{curatorEmail}/{groupName}/meetings")
    ResponseEntity<MeetingDto> createGroupMeetings(@PathVariable String curatorEmail, @PathVariable String groupName, @RequestBody MeetingDto meeting) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        return curatorService.createGroupMeetings(curatorEmail, groupName, meeting);
    }
}

//    @GetMapping("/curator/")
//    ResponseEntity<CuratorDto> getCuratorInfo(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
//        String jwtToken = authHeader.substring(7);
//        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
//        return curatorService.getCuratorByEmail(curatorEmail);
//    }
//    @GetMapping("/curator/groups")
//    ResponseEntity<Set<GroupDto>> getCuratorGroups(@RequestHeader("Authorization") String authHeader) throws UserNotFoundByUsernameException {
//        String jwtToken = authHeader.substring(7);
//        String curatorEmail = jwtTokenUtils.getUsername(jwtToken);
//        return curatorService.getCuratorGroups(curatorEmail);
//    }