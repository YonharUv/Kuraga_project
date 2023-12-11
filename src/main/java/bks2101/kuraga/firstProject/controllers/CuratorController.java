package bks2101.kuraga.firstProject.controllers;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.MeetingDto;
import bks2101.kuraga.firstProject.dto.StudentDto;
import bks2101.kuraga.firstProject.exceptions.GroupNotFoundByCurator;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.service.CuratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class CuratorController {
    private final CuratorService curatorService;
    @GetMapping("/curators/{curatorEmail}")
    ResponseEntity<CuratorDto> getCuratorByName(@PathVariable String curatorEmail) throws UserNotFoundByUsernameException {
        return curatorService.getCuratorByEmail(curatorEmail);
    }
    @GetMapping("/curators/{curatorEmail}/groups")
    ResponseEntity<Set<GroupDto>> getCuratorGroupsByName(@PathVariable String curatorEmail) throws UserNotFoundByUsernameException {
        return curatorService.getCuratorGroups(curatorEmail);
    }
    @GetMapping("/curators/{curatorEmail}/groups/meetings")
    ResponseEntity<Set<MeetingDto>> getCuratorMeetingsByName(@PathVariable String curatorEmail) throws UserNotFoundByUsernameException {
        return curatorService.getCuratorMeetings(curatorEmail);
    }
    @GetMapping("/curators/{curatorEmail}/{groupName}/")
    ResponseEntity<GroupDto> getCuratorGroupByName(@PathVariable String curatorEmail, @PathVariable String groupName) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        return curatorService.getCuratorGroupByName(curatorEmail, groupName);
    }
    @PostMapping("/curator/{curatorEmail}/{groupName}/meetings")
    ResponseEntity<MeetingDto> createGroupMeetings(@PathVariable String curatorEmail, @PathVariable String groupName, @RequestBody MeetingDto meeting) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        return curatorService.createGroupMeetings(curatorEmail, groupName, meeting);
    }
}
