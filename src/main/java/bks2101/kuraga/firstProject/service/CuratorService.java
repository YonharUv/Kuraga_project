package bks2101.kuraga.firstProject.service;

import bks2101.kuraga.firstProject.dto.CuratorDto;
import bks2101.kuraga.firstProject.dto.CuratorResponse;
import bks2101.kuraga.firstProject.dto.GroupDto;
import bks2101.kuraga.firstProject.dto.MeetingDto;
import bks2101.kuraga.firstProject.exceptions.GroupNotFoundByCurator;
import bks2101.kuraga.firstProject.exceptions.UserAlreadyExistsException;
import bks2101.kuraga.firstProject.exceptions.UserNotFoundByUsernameException;
import bks2101.kuraga.firstProject.models.Curator;
import bks2101.kuraga.firstProject.models.Group;
import bks2101.kuraga.firstProject.models.Meeting;
import bks2101.kuraga.firstProject.models.Supervisor;
import bks2101.kuraga.firstProject.repository.CuratorRepository;
import bks2101.kuraga.firstProject.repository.GroupRepository;
import bks2101.kuraga.firstProject.repository.SupervisorRepository;
import bks2101.kuraga.firstProject.utils.JwtTokenUtils;
import bks2101.kuraga.firstProject.utils.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class CuratorService {
    private final CuratorRepository curatorRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final SupervisorRepository supervisorRepository;
    private final GroupRepository groupRepository;
    public ResponseEntity<List<CuratorDto>> getAllCurators() {
        var listCurators = curatorRepository.findAll();
        List<CuratorDto> curatorDtoList = listCurators.stream()
                .map(MappingUtil::mapToCuratorDto)
                .toList();
        return ResponseEntity.ok(curatorDtoList);
    }
    public ResponseEntity<String> deleteCuratorByEmail(String email) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(email)) {
            throw new UserNotFoundByUsernameException("Куратор", email);
        }
        curatorRepository.delete(curatorRepository.getByEmail(email));
        return ResponseEntity.ok("Куратор" + email + "успешно удален");
    }
    public ResponseEntity<String> createCurator(String authHeader, CuratorDto curatorDto) throws UserAlreadyExistsException {
        String jwtToken = authHeader.substring(7);
        String email = jwtTokenUtils.getUsername(jwtToken);
        Curator curator = new Curator();
        curator.setFirst_name(curatorDto.getFirst_name());
        curator.setLast_name(curatorDto.getLast_name());
        curator.setPersonal_data(curatorDto.getPersonal_data());
        curator.setEmail(email);
        Supervisor supervisor = supervisorRepository.findByEmail(curatorDto.getSupervisor_email());
        supervisor.addCurator(curator);
        curator.setSupervisor(supervisor);
        supervisorRepository.save(supervisor);
        curatorRepository.save(curator);
        return ResponseEntity.ok("Куратор успешно создан");
    }

    public ResponseEntity<String> createCuratorAdmin(CuratorDto curatorDto) throws UserAlreadyExistsException {
        Curator curator = new Curator();
        curator.setFirst_name(curatorDto.getFirst_name());
        curator.setLast_name(curatorDto.getLast_name());
        curator.setPersonal_data(curatorDto.getPersonal_data());
        curator.setEmail(curatorDto.getEmail());
        Supervisor supervisor = supervisorRepository.findByEmail(curatorDto.getSupervisor_email());
        supervisor.addCurator(curator);
        curator.setSupervisor(supervisor);
        supervisorRepository.save(supervisor);
        curatorRepository.save(curator);
        return ResponseEntity.ok("Куратор успешно создан");
    }

    public ResponseEntity<CuratorDto> getCuratorByEmail(String curatorEmail) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        return ResponseEntity.ok(MappingUtil.mapToCuratorDto(curator));
    }
    public ResponseEntity<Set<GroupDto>> getCuratorGroups(String curatorEmail) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        return ResponseEntity.ok(curatorDto.getGroups());
    }
    public ResponseEntity<Set<MeetingDto>> getCuratorMeetings(String curatorEmail) throws UserNotFoundByUsernameException {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        return ResponseEntity.ok(MappingUtil.mapToMeetingDto(curatorDto));
    }

    public ResponseEntity<GroupDto> getCuratorGroupByName(String curatorEmail, String groupName) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        if (!curatorDto.getGroups().contains(groupName)) {
            throw new GroupNotFoundByCurator(curatorEmail, groupName);
        }
        return ResponseEntity.ok(MappingUtil.mapToGroupDto(groupRepository.findByName(groupName)));
    }

    public ResponseEntity<MeetingDto> createGroupMeetings(String curatorEmail, String groupName, MeetingDto meetingDto) throws UserNotFoundByUsernameException, GroupNotFoundByCurator {
        if (!curatorRepository.existsByEmail(curatorEmail)) {
            throw new UserNotFoundByUsernameException("Куратор", curatorEmail);
        }
        Curator curator = curatorRepository.getByEmail(curatorEmail);
        CuratorDto curatorDto = MappingUtil.mapToCuratorDto(curator);
        if (!curatorDto.getGroups().contains(groupName)) {
            throw new GroupNotFoundByCurator(curatorEmail, groupName);
        }
        Group group = groupRepository.findByName(groupName);
        Meeting meeting = MappingUtil.mapToMeeting(curator, meetingDto, group);
        curator.AddMeetings(meeting);
        group.AddMeeting(meeting);
        curatorRepository.save(curator);
        groupRepository.save(group);
        return ResponseEntity.ok(meetingDto);
    }
}
